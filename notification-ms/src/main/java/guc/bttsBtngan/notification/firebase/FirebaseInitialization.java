package guc.bttsBtngan.notification.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


@Service
public class FirebaseInitialization {
    @PostConstruct
    public void initialization(){

        FileInputStream serviceAccount =
                null;
        try {//check directory
            serviceAccount = new FileInputStream("notification-ms/gaming-platform-notificationms-firebase-adminsdk-merdm-c95c9d247b.json");


        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
