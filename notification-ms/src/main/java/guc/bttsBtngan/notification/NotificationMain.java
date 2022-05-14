package guc.bttsBtngan.notification;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


@SpringBootApplication
public class NotificationMain {

	public static void main(String[] args) throws IOException {
		//SpringApplication.run(NotificationMain.class, args);
	//	ClassLoader classLoader = NotificationMain.class.getClassLoader();
//		File file = new File(Objects.requireNonNull(classLoader
//				.getResource("gaming-platform-notificationms-firebase-adminsdk-merdm-c95c9d247b.json")).getFile());


//		InputStream serviceAccount = new FileInputStream("notification-ms/gaming-platform-notificationms-firebase-adminsdk-merdm-c95c9d247b.json");
//		GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
//		FirebaseOptions options = new FirebaseOptions.Builder()
//				.setCredentials(credentials)
//				.build();
//		FirebaseApp.initializeApp(options);
		SpringApplication.run(NotificationMain.class, args);
	//	FirestoreClient.getFirestore();

	}

}
