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
    public String createNotification(Notifications notification){

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

//    public Notifications getNotification(String name) throws ExecutionException, InterruptedException {
//        Firestore firestoredb= FirestoreClient.getFirestore();
//
//        DocumentReference documentReference= firestoredb.collection("notification").document(name);
//
//        ApiFuture<DocumentSnapshot> future=documentReference.get();
//
//        DocumentSnapshot document=future.get();
//
//        Notifications notifications=null;
//        if(document.exists()){
//            notifications=document.toObject(Notifications.class);
//            return notifications;
//        }else{
//            return null;
//        }
//    }

        public ArrayList<String> getNotification(String userID) throws ExecutionException, InterruptedException {
            Firestore firestoredb = FirestoreClient.getFirestore();
            ArrayList<String>notificationTypes=new ArrayList<String>();
            Iterable<DocumentReference> documentReference = firestoredb.collection("notification").listDocuments();
            Iterator<DocumentReference> iterator=documentReference.iterator();
            while (iterator.hasNext()) {
                ApiFuture<DocumentSnapshot> future = iterator.next().get();
//                System.out.println(documentReference.iterator().next().get());
//                System.out.println(documentReference);
//                System.out.println("\nhiiiiiiiiiiiiiiiiiiiii\n");

                DocumentSnapshot document = future.get();

                Notifications notifications = null;
                if (document.exists()) {
                    notifications = document.toObject(Notifications.class);
               //     System.out.println(document);
                  //  System.out.println(notifications.getUserIDs().contains(userID));
                    if(notifications.getUserIDs()!=null&&contains(notifications.getUserIDs(),userID)){
                        System.out.println(notifications.getUserIDs().get(0));
                        notificationTypes.add(notifications.getType());
                    }
                }
              //  documentReference.iterator().next();
            }
            return notificationTypes;
        }

    private boolean contains(List<String> userIDs, String userID) {
        System.out.println(userID);
        for (int i=0;i<userIDs.size();i++){
            if(userID.equals(userIDs.get(i).toString())){
                return true;
            }
        }
        return false;
    }
}
