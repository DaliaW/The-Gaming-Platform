package guc.bttsBtngan.chat.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import guc.bttsBtngan.chat.amqp.RabbitMQConfig;
import guc.bttsBtngan.chat.data.ChatMessage;
import guc.bttsBtngan.chat.data.PrivateChat;

@Service
public class PrivateChatService {
	
	@Autowired
	AmqpTemplate amqpTemplate;
	
	public String createPrivateChat(PrivateChat chat) throws Exception {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference doc_ref = db.collection("private_chat").document(chat.getPrivate_id());
		ApiFuture<DocumentSnapshot> future = doc_ref.get();
		DocumentSnapshot document = future.get();
		
		if(document.exists()) {
			throw new Exception("This chat already exists");
		}
		
		ApiFuture<WriteResult> doc_ref2 = db.collection("private_chat").document(chat.getPrivate_id()).set(chat);
		return "Added private chat at timestamp: " + doc_ref2.get().getUpdateTime();
	}
	
	public String sendPrivateMessage(String user_id, String private_id, String content, String timestamp) throws Exception {
		
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference doc_ref = db.collection("private_chat").document(private_id);
		ApiFuture<DocumentSnapshot> future = doc_ref.get();
		DocumentSnapshot document = future.get();
		if(document.exists()) {
			PrivateChat chat = document.toObject(PrivateChat.class);
			if(!chat.getUser_1().equals(user_id) && !chat.getUser_2().equals(user_id)) 
				throw new Exception("You are not a member of the private chat with id: " + private_id);
			HashMap<String , Object> map = new HashMap<>();
			String message_id = UUID.randomUUID().toString();
			map.put("sender_id", user_id);
			map.put("content", content);
			map.put("timestamp", timestamp);
			map.put("message_id", message_id);
			ApiFuture<WriteResult> ref = db.collection("private_chat").document(private_id)
					.collection("messages").document(message_id).set(map);
			Map<String, Object> notificationMap = new HashMap<>();
			notificationMap.put("type", "message");
			List<String> list = new ArrayList<>();
			list.add(chat.getUser_1().equals(user_id) ? chat.getUser_2() : chat.getUser_1());
			notificationMap.put("userIDs", list);
			amqpTemplate.convertAndSend(RabbitMQConfig.notifications_queue, notificationMap);
			return "Added message with id: " + message_id;
		} else {
			throw new Exception("No private chat exists with id: " + private_id);
		}
		
	}
	
	public String deletePrivateChat(String user_id, String private_id) throws Exception {
		
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference doc_ref = db.collection("private_chat").document(private_id);
		
		ApiFuture<DocumentSnapshot> future = doc_ref.get();
		DocumentSnapshot document = future.get();
		
		if(document.exists()) {
			PrivateChat chat = document.toObject(PrivateChat.class);
			if(!chat.getUser_1().equals(user_id) && !chat.getUser_2().equals(user_id)) 
				throw new Exception("You are not a member of the private chat with id: " + private_id);
			ApiFuture<WriteResult> writeResult = doc_ref.delete();
			return "Update time : " + writeResult.get().getUpdateTime();
		} else {
			throw new Exception("No private chat exists with id: " + private_id);
		}
		
	}
	
	public String deletePrivateMessage(String user_id, String private_id, String message_id) throws Exception {
		
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference doc_ref = db.collection("private_chat").document(private_id).collection("messages").document(message_id);
		
		ApiFuture<DocumentSnapshot> future = doc_ref.get();
		DocumentSnapshot document = future.get();
		
		if(document.exists()) {
			ChatMessage message = document.toObject(ChatMessage.class);
			if(!message.getSender_id().equals(user_id)) 
				throw new Exception("You didn't send this message: " + message_id);
			ApiFuture<WriteResult> writeResult = doc_ref.delete();
			return "Update time : " + writeResult.get().getUpdateTime();
		} else {
			throw new Exception("No private chat exists with id: " + private_id);
		}
		
	}
	
	public String updateMessage(String user_id, String private_id, String message_id, String content) throws Exception {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference doc_ref = db.collection("private_chat").document(private_id).collection("messages").document(message_id);
		ApiFuture<DocumentSnapshot> future = doc_ref.get();
		DocumentSnapshot document = future.get();
		if(document.exists()) {
			ChatMessage msg = document.toObject(ChatMessage.class);
			if(!msg.getSender_id().equals(user_id)) {
				throw new Exception("user " + user_id + " is not allowed to update this message"); 
			}
			try {
				ApiFuture<WriteResult> writeResult = doc_ref.update("content", content);
				return "Update time : " + writeResult.get().getUpdateTime();
			} catch(Exception e) {
				throw new Exception("Error while updating message: " + e.getMessage());
			}

		} else {
			throw new Exception("No message exists with id: " + message_id);
		}
	}
	
	public Map<String, Object> getPrivateChat(String user_id, String private_id) throws Exception {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference doc_ref = db.collection("private_chat").document(private_id);
		ApiFuture<DocumentSnapshot> future = doc_ref.get();
		ApiFuture<QuerySnapshot> future2 = doc_ref.collection("messages").get();
		DocumentSnapshot document = future.get();
		if(document.exists()) {
			Map<String, Object> gp = document.getData();
			
			if(!gp.get("user_1").equals(user_id) && !gp.get("user_2").equals(user_id)) {
				throw new Exception("You are not authorized to view this chat: " + private_id);
			}
			
			List<ChatMessage> msgs = new ArrayList<>();
			List<QueryDocumentSnapshot> documents = future2.get().getDocuments();
			for (QueryDocumentSnapshot message : documents) {
				msgs.add(message.toObject(ChatMessage.class));
			}
			gp.put("messages", msgs);
			return gp;
		} else {
			throw new Exception("No private chat exists with id: " + private_id);
		}

	}
}
