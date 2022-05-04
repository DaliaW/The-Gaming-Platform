package guc.bttsBtngan.chat.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import guc.bttsBtngan.chat.data.ChatMessage;
import guc.bttsBtngan.chat.data.GroupChat;

@Service
public class GroupChatService {
	
	//TODO: add notifications to appropriate functions
	
	public String createGroup(GroupChat chat) throws InterruptedException, ExecutionException {
		List<String> participants = new ArrayList<>();
		participants.add(chat.getAdmin_id());
		chat.setParticipants(participants);
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<DocumentReference> doc_ref = db.collection("group_chat").add(chat);
		return "Added group with ID: " + doc_ref.get().getId();
	}
	
	public String changeAdmin(String user_id, String new_admin_id, String group_id) throws Exception {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference doc_ref = db.collection("group_chat").document(group_id);
		ApiFuture<DocumentSnapshot> future = doc_ref.get();
		DocumentSnapshot document = future.get();
		if(document.exists()) {
			GroupChat chat = document.toObject(GroupChat.class);
			if(!chat.getAdmin_id().equals(user_id)) {
				throw new Exception("you are not the admin of this group");
			}
			if(chat.getParticipants().contains(new_admin_id)) {
				ApiFuture<WriteResult> future2 = doc_ref.update("admin_id", new_admin_id);
				WriteResult result = future2.get();
				return "Write result: " + result;
			}
			throw new Exception("the new admin must be a member of the group");
		} else {
			throw new Exception("No group exists with id: " + group_id);
		}
	}
	
	public String changeGroupName(String user_id, String new_name, String group_id) throws Exception {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference doc_ref = db.collection("group_chat").document(group_id);
		ApiFuture<DocumentSnapshot> future = doc_ref.get();
		DocumentSnapshot document = future.get();
		if(document.exists()) {
			GroupChat chat = document.toObject(GroupChat.class);
			if(!chat.getAdmin_id().equals(user_id)) {
				throw new Exception("you are not the admin of this group");
			}
			ApiFuture<WriteResult> future2 = doc_ref.update("name", new_name);
			WriteResult result = future2.get();
			return "Write result: " + result;
		} else {
			throw new Exception("No group exists with id: " + group_id);
		}
	}
	
	public String joinGroup(String user_id, String group_id) throws Exception {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference doc_ref = db.collection("group_chat").document(group_id);
		ApiFuture<DocumentSnapshot> future = doc_ref.get();
		DocumentSnapshot document = future.get();
		if(document.exists()) {
			ApiFuture<WriteResult> future2 = doc_ref.update("participants", FieldValue.arrayUnion(user_id));
			WriteResult result = future2.get();
			return "Write result: " + result;
		} else {
			throw new Exception("No group exists with id: " + group_id);
		}
	}
	
	public String leaveGroup(String user_id, String group_id) throws Exception {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference doc_ref = db.collection("group_chat").document(group_id);
		ApiFuture<DocumentSnapshot> future = doc_ref.get();
		DocumentSnapshot document = future.get();
		if(document.exists()) {
			ApiFuture<WriteResult> future2 = doc_ref.update("participants", FieldValue.arrayRemove(user_id));
			WriteResult result = future2.get();
			return "Write result: " + result;
		} else {
			throw new Exception("No group exists with id: " + group_id);
		}
	}
	
	public String sendGroupMessage(String user_id, String group_id, String content) throws Exception {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference doc_ref = db.collection("group_chat").document(group_id);
		ApiFuture<DocumentSnapshot> future = doc_ref.get();
		DocumentSnapshot document = future.get();
		if(document.exists()) {
			GroupChat chat = document.toObject(GroupChat.class);
			if(!chat.getParticipants().contains(user_id)) 
				throw new Exception("You are not a member of the group with id: " + group_id);
			HashMap<String , Object> map = new HashMap<>();
			map.put("sender_id", user_id);
			map.put("content", content);
			map.put("timestamp", FieldValue.serverTimestamp());
			ApiFuture<DocumentReference> ref = db.collection("group_chat").document(group_id)
					.collection("messages").add(map);
			return "Added message with id: " + ref.get().getId();
		} else {
			throw new Exception("No group exists with id: " + group_id);
		}
	}
	
	public String deleteGroup(String group_id, String user_id) throws Exception {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference doc_ref = db.collection("group_chat").document(group_id);
		ApiFuture<DocumentSnapshot> future = doc_ref.get();
		DocumentSnapshot document = future.get();
		if(document.exists()) {
			GroupChat chat = document.toObject(GroupChat.class);
			if(!chat.getAdmin_id().equals(user_id)) {
				throw new Exception("you are not the admin of this group");
			}
			deleteCollection(doc_ref.collection("messages"), 1000);
			ApiFuture<WriteResult> writeResult = doc_ref.delete();
			return "Update time : " + writeResult.get().getUpdateTime();
		} else {
			throw new Exception("No group exists with id: " + group_id);
		}
	}
	
	public String deleteMessage(String group_id, String user_id, String message_id) throws Exception {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference doc_ref = db.collection("group_chat").document(group_id)
				.collection("messages").document(message_id);
		ApiFuture<DocumentSnapshot> future = doc_ref.get();
		DocumentSnapshot document = future.get();
		if(document.exists()) {
			ChatMessage msg = document.toObject(ChatMessage.class);
			if(!msg.getSender_id().equals(user_id)) {
				throw new Exception("user " + user_id + " is not allowed to delete this message"); 
			}
			ApiFuture<WriteResult> writeResult = doc_ref.delete();
			return "Update time : " + writeResult.get().getUpdateTime();
		} else {
			throw new Exception("No message exists with id: " + message_id);
		}
	}
	
	public String updateMessage(String group_id, String user_id, String message_id, String content) throws Exception {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference doc_ref = db.collection("group_chat").document(group_id)
				.collection("messages").document(message_id);
		ApiFuture<DocumentSnapshot> future = doc_ref.get();
		DocumentSnapshot document = future.get();
		if(document.exists()) {
			ChatMessage msg = document.toObject(ChatMessage.class);
			if(!msg.getSender_id().equals(user_id)) {
				throw new Exception("user " + user_id + " is not allowed to update this message"); 
			}
			ApiFuture<WriteResult> writeResult = doc_ref.update("content", content);
			return "Update time : " + writeResult.get().getUpdateTime();
		} else {
			throw new Exception("No message exists with id: " + message_id);
		}
	}
	
	public Map<String, Object> getGroup(String group_id) throws Exception {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference doc_ref = db.collection("group_chat").document(group_id);
		ApiFuture<DocumentSnapshot> future = doc_ref.get();
		ApiFuture<QuerySnapshot> future2 = doc_ref.collection("messages").get();
		DocumentSnapshot document = future.get();
		if(document.exists()) {
			Map<String, Object> gp = document.getData();
			List<ChatMessage> msgs = new ArrayList<>();
			List<QueryDocumentSnapshot> documents = future2.get().getDocuments();
			for (QueryDocumentSnapshot message : documents) {
				msgs.add(message.toObject(ChatMessage.class));
			}
			gp.put("messages", msgs);
			return gp;
		} else {
			throw new Exception("No group exists with id: " + group_id);
		}

	}
	
	public void deleteCollection(CollectionReference collection, int batchSize) throws InterruptedException, ExecutionException {
		// retrieve a small batch of documents to avoid out-of-memory errors
	    ApiFuture<QuerySnapshot> future = collection.limit(batchSize).get();
	    int deleted = 0;
	    // future.get() blocks on document retrieval
	    List<QueryDocumentSnapshot> documents = future.get().getDocuments();
	    for (QueryDocumentSnapshot document : documents) {
	      document.getReference().delete();
	      ++deleted;
	    }
	    if (deleted >= batchSize) {
	      // retrieve and delete another batch
	      deleteCollection(collection, batchSize);
	    }
	}
}
