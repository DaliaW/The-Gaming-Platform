package guc.bttsBtngan.post.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import guc.bttsBtngan.post.data.Post;
import org.springframework.stereotype.Service;

//import com.google.api.core.ApiFuture;
//import com.google.cloud.firestore.DocumentReference;
//import com.google.cloud.firestore.DocumentSnapshot;
//import com.google.cloud.firestore.FieldValue;
//import com.google.cloud.firestore.Firestore;
//import com.google.cloud.firestore.WriteResult;
//import com.google.firebase.cloud.FirestoreClient;


@Service
public class PostService {

    public String createPost() throws InterruptedException, ExecutionException {
        Post post = new Post();
        post.setContent("btats potatoeeesss");
        post.setUserId("id gamed");
//        Firestore db = FirestoreClient.getFirestore();
//        ApiFuture<DocumentReference> doc_ref = db.collection("group_chat").add(post);
//        return "Added group with ID: " + doc_ref.get().getId();
        return "DONE, created post is: "+(post).toString();
    }
}