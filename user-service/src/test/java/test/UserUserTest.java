package test;
import guc.bttsBtngan.user.UserMain;
import guc.bttsBtngan.user.data.UserPostInteraction;
import guc.bttsBtngan.user.data.UserUserInteraction;
import guc.bttsBtngan.user.services.UserUserService;
import guc.bttsBtngan.user.services.UserRepository;
import guc.bttsBtngan.user.services.UserPostService;
import guc.bttsBtngan.user.services.UserPostRepository;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;

@SpringBootTest(classes = UserMain.class)
@ActiveProfiles("test") //@ActiveProfiles("guc/bttsBtngan/user/test")
@RunWith(SpringJUnit4ClassRunner.class)

public class UserUserTest {
//    @Autowired
    private UserPostService userPostService;

    @Autowired
    private UserUserService userUserService;

//    @MockBean
    @Autowired
    private UserRepository userRepository;

//    @MockBean
    @Autowired
    private UserPostRepository userPostRepository;


    @Test
    public void deleteUserTest() throws Exception {
//        UUID uuid = UUID.randomUUID();
//        String userID = "";
//        userID=uuid.toString();
        String userID= "2c9ec08180fd8fce0180fd8ffbf40011";
        UserUserInteraction User = new UserUserInteraction();
        User.setid(userID);
        User.setEmail("a2@gmail.com");
        User.setusername("u2");
        User.setPassword("p2");
        User.setPhotoRef("pr2");

        System.out.println("USER added "+ User);
        System.out.println("before anything "+userUserService.getAllUsers());
        System.out.println("length 111 "+ userUserService.getAllUsers().length());

//        userUserService.registerUser(User);
//        System.out.println(userUserService);
//        Optional<UserUserInteraction> userX = userRepository.findById(userID);
//        System.out.println("USER x "+ userX);

        try {
            System.out.println("ana henaa ");
            userUserService.registerUser(User);
//            String result = userUserService.registerUser(User);
            System.out.println("after adding the user :  "+userUserService.getAllUsers());
            System.out.println("length 1 "+userUserService.getAllUsers());
//            System.out.println("reulst :  "+result);

//            System.out.println(userUserService);
//            userRepository.save(User);
//            System.out.println(userRepository);
//            UserPostInteraction user = userPostRepository.findByUserId(userID);
//            Optional<UserUserInteraction> output = userRepository.findById(userID);
//            System.out.println("USER x "+ output);
            userUserService.DeleteUser(userID);
            System.out.println("after deletion "+userUserService.getAllUsers());
//            userUserService = Mockito.mock(UserUserService.class);
//            ArrayList<String> mockOut = new ArrayList<String>();
////            Optional<UserUserInteraction> userOutput = Optional.of(new UserUserInteraction());
////            Mockito.when(userRepository.findById(userID)).thenReturn(mockOut);
//            Assert.assertEquals(userRepository.findById(userID), output);


        } catch (Exception e) {
            System.out.println(userID);
            throw new RuntimeException(e);
        }


    }
//    @Test
//    public void getAllUsersTest() {
////        when(userRepository.findAll()).thenReturn(Stream.of(new ))
////        Mockito.when(productService.getProductName()).thenReturn("Mock Product Name");
////        String testName = orderService.getProductName();
////        Assert.assertEquals("Mock Product Name", testName);
//    }

}