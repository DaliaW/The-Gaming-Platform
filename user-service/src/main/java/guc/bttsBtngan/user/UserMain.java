package guc.bttsBtngan.user;

import guc.bttsBtngan.user.data.UserPostInteraction;
import guc.bttsBtngan.user.services.UserPostRepository;
import javafx.scene.canvas.GraphicsContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories
@SpringBootApplication
public class UserMain {
	//CREATE
	@Autowired
	UserPostRepository userPostRepository;
	@Autowired
	MongoOperations mongoOperations;
	public static void main(String[] args) {
		SpringApplication.run(UserMain.class, args);
		System.out.println("-------------CREATE Dummy-------------------------------\n");
		UserMain x=new UserMain();
		x.createDummyData();

		System.out.println("\n-------------------THANK YOU---------------------------");
	}




	 void createDummyData() {
		System.out.println("Data creation started...");
		List<String> followers = new java.util.ArrayList<>();
		List<String> following = new java.util.ArrayList<>();
		List<String> blockedBy = new java.util.ArrayList<>();
		followers.add("user1");
		followers.add("user2");
		following.add("user3");
		following.add("user4");
		blockedBy.add("user5");
		blockedBy.add("user6");
		 System.out.println("f= "+followers.toString());
		 System.out.println("f2= "+following.toString());
		 System.out.println("f3= "+blockedBy.toString());
		 UserPostInteraction x= new UserPostInteraction("user1", followers, following, blockedBy);
		 System.out.println("created");
		 mongoOperations.save(x);
		System.out.println("Data creation finished.");
	}

	public void run(String... args) {

		System.out.println("-------------CREATE Dummy-------------------------------\n");

		createDummyData();

		System.out.println("\n-------------------THANK YOU---------------------------");

	}
}
