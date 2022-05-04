package guc.bttsBtngan.notification.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import guc.bttsBtngan.notification.entity.Notifications;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ExecutionException;

@Service
public class NotificationService {
    public String saveNotification(Notifications notification){

        Firestore firestoredb= FirestoreClient.getFirestore();

     //   ApiFuture<WriteResult> collectionApi= firestoredb.collection("notification").document(notification.getType()).set(notification);
        ApiFuture<WriteResult> collectionApi= firestoredb.collection("notification").document().create(notification);

        try {
            return collectionApi.get().getUpdateTime().toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public String getNotification()
}
