package guc.bttsBtngan.chat.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import guc.bttsBtngan.chat.data.GroupChat;

@Service
public class GroupChatService {
	
	
	public String createGroup(GroupChat chat) throws InterruptedException, ExecutionException {
		List<String> participants = new ArrayList<>();
		participants.add(chat.getAdmin_id());
		chat.setParticipants(participants);
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<DocumentReference> doc_ref = db.collection("group_chat").add(chat);
		return "Added group with ID: " + doc_ref.get().getId();
	}
	
	public String changeAdmin(String user_id, String new_admin_id, String group_id) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference doc_ref = db.collection("group_chat").document(group_id);
		ApiFuture<DocumentSnapshot> future = doc_ref.get();
		DocumentSnapshot document = future.get();
		if(document.exists()) {
			GroupChat chat = document.toObject(GroupChat.class);
			if(!chat.getAdmin_id().equals(user_id)) {
				return "you are not the admin of this group";
			}
			ApiFuture<WriteResult> future2 = doc_ref.update("admin_id", new_admin_id);
			WriteResult result = future2.get();
			return "Write result: " + result;
		} else {
			return "No group exists with id: " + group_id;
		}
	}
	
	
}
