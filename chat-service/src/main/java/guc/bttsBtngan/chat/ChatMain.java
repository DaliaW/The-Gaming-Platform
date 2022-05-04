package guc.bttsBtngan.chat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.auth.oauth2.GoogleCredentials;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@SpringBootApplication
public class ChatMain {

	public static void main(String[] args) throws IOException {
		ClassLoader classLoader = ChatMain.class.getClassLoader();
		File file = new File(Objects.requireNonNull(classLoader
				.getResource("firebase_credentials.json")).getFile());
		InputStream serviceAccount = new FileInputStream(file.getAbsolutePath());
		GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
		FirebaseOptions options = new FirebaseOptions.Builder()
		    .setCredentials(credentials)
		    .build();
		FirebaseApp.initializeApp(options);
		SpringApplication.run(ChatMain.class, args);
		FirestoreClient.getFirestore();
	}

}
