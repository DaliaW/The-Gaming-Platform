package guc.bttsBtngan.notification.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import guc.bttsBtngan.notification.entity.Notifications;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class NotificationService<retun> {
    public String createNotification(Notifications notification) throws ExecutionException, InterruptedException {

        Firestore firestoredb= FirestoreClient.getFirestore();

        ApiFuture<WriteResult> collectionApi= firestoredb.collection("notification").document().create(notification);

        return "Notification Created, "+collectionApi.get().getUpdateTime().toString();

    }


    public String updateNotification(String notificationID,Notifications notification) throws Exception {

        Firestore firestoredb= FirestoreClient.getFirestore();

        DocumentReference documentReference=firestoredb.collection("notification").document(notificationID);
        ApiFuture<DocumentSnapshot> future=documentReference.get();
        DocumentSnapshot document=future.get();
        if(document.exists()){
            ApiFuture<WriteResult> collectionApi= documentReference.set(notification);
            return "Notification Updated, "+collectionApi.get().getUpdateTime().toString();
        }else{
            throw new Exception("No notification exists with id: " + notificationID);
        }

    }

    public String deleteNotification(String notificationID,String userID) throws Exception {
        Firestore firestoredb= FirestoreClient.getFirestore();
        DocumentReference documentReference=firestoredb.collection("notification").document(notificationID);
        ApiFuture<DocumentSnapshot> future=documentReference.get();
        DocumentSnapshot document=future.get();
        Notifications notifications=null;
        if(document.exists()){
            notifications=document.toObject(Notifications.class);
            List<String> oldList=notifications.getUserIDs();
            boolean flag=oldList.remove(userID);
            if(flag) {
                notifications.setUserIDs(oldList);
                ApiFuture<WriteResult> collectionApi = documentReference.set(notifications);

                return "Notification Deleted for userID " + userID + ", at: " + collectionApi.get().getUpdateTime().toString();
            }else{
                throw new Exception("No user: "+userID+" exists in this notification: " + notificationID);
            }
        }else{
            throw new Exception("No notification exists with id: " + notificationID);
        }

    }


    public ArrayList<String> getNotification(String userID) throws ExecutionException, InterruptedException {
        Firestore firestoredb = FirestoreClient.getFirestore();
        ArrayList<String>notificationTypes=new ArrayList<String>();
        Iterable<DocumentReference> documentReference = firestoredb.collection("notification").listDocuments();
        Iterator<DocumentReference> iterator=documentReference.iterator();
        while (iterator.hasNext()) {
            ApiFuture<DocumentSnapshot> future = iterator.next().get();
            DocumentSnapshot document = future.get();

            Notifications notifications = null;
            if (document.exists()) {
                notifications = document.toObject(Notifications.class);
                if(notifications.getUserIDs()!=null&&notifications.getUserIDs().contains(userID)){
                    notificationTypes.add(notifications.getType());
                }
            }
        }
        return notificationTypes;
    }

}
