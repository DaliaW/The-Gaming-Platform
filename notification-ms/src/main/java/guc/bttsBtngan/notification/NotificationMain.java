package guc.bttsBtngan.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.firestore.Firestore;
//
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import com.google.firebase.cloud.FirestoreClient;


@SpringBootApplication
public class NotificationMain {

	public static void main(String[] args) {
		SpringApplication.run(NotificationMain.class, args);
//		ClassLoader classLoader = NotificationMain.class.getClassLoader();
//		File file = new File(Objects.requireNonNull(classLoader
//				.getResource("firebase_credentials.json")).getFile());
//		InputStream serviceAccount = new FileInputStream(file.getAbsolutePath());
//		GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
//		FirebaseOptions options = new FirebaseOptions.Builder()
//				.setCredentials(credentials)
//				.build();
//		FirebaseApp.initializeApp(options);
//		SpringApplication.run(NotificationMain.class, args);
//		FirestoreClient.getFirestore();
	}

}
