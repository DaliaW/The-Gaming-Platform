package guc.bttsBtngan.user.services;

import guc.bttsBtngan.user.data.UserPostInteraction;
import guc.bttsBtngan.user.data.UserUserInteraction;
import guc.bttsBtngan.user.firebase.FirebaseImageService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserUserService.class})
@ExtendWith(SpringExtension.class)
class UserUserServiceTest {
    @MockBean
    private AmqpTemplate amqpTemplate;

    @MockBean
    private FirebaseImageService firebaseImageService;

    @MockBean
    private MongoOperations mongoOperations;

    @MockBean
    private UserPostRepository userPostRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserUserService userUserService;

    /**
     * Method under test: {@link UserUserService#getAllUsers()}
     */
    @Test
    void testGetAllUsers() {
        when(this.userRepository.findAll()).thenReturn(new ArrayList<>());
        assertEquals("[]", this.userUserService.getAllUsers());
        verify(this.userRepository).findAll();
    }

    /**
     * Method under test: {@link UserUserService#getAllUsers()}
     */
    @Test
    void testGetAllUsers2() {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");

        ArrayList<UserUserInteraction> userUserInteractionList = new ArrayList<>();
        userUserInteractionList.add(userUserInteraction);
        when(this.userRepository.findAll()).thenReturn(userUserInteractionList);
        assertEquals("[UserUserInteraction{id='42', username='janedoe', Password='pass', email='jane.doe@example.org',"
                + " photoRef='null', isModerator=true, isBanned=true}]", this.userUserService.getAllUsers());
        verify(this.userRepository).findAll();
    }

    /**
     * Method under test: {@link UserUserService#getAllUsers()}
     */
    @Test
    void testGetAllUsers3() {
        when(this.userRepository.findAll()).thenThrow(new IllegalArgumentException("foo"));
        assertThrows(IllegalArgumentException.class, () -> this.userUserService.getAllUsers());
        verify(this.userRepository).findAll();
    }

    /**
     * Method under test: {@link UserUserService#getAllUsers()}
     */
    @Test
    void testGetAllUsers4() {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");

        ArrayList<UserUserInteraction> userUserInteractionList = new ArrayList<>();
        userUserInteractionList.add(userUserInteraction1);
        userUserInteractionList.add(userUserInteraction);
        when(this.userRepository.findAll()).thenReturn(userUserInteractionList);
        assertEquals("[UserUserInteraction{id='42', username='janedoe', Password='pass', email='jane.doe@example.org',"
                        + " photoRef='null', isModerator=true, isBanned=true}, UserUserInteraction{id='42', username='janedoe',"
                        + " Password='pass', email='jane.doe@example.org', photoRef='null', isModerator=true, isBanned" + "=true}]",
                this.userUserService.getAllUsers());
        verify(this.userRepository).findAll();
    }

    /**
     * Method under test: {@link UserUserService#registerUser(UserUserInteraction)}
     */
    @Test
    void testRegisterUser() {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("Pass123");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("Pass123");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction1);

        UserUserInteraction userUserInteraction2 = new UserUserInteraction();
        userUserInteraction2.setBanned(true);
        userUserInteraction2.setEmail("jane.doe@example.org");
        userUserInteraction2.setModerator(true);
        userUserInteraction2.setPassword("Pass123");
        userUserInteraction2.setid("42");
        userUserInteraction2.setusername("janedoe");
        Optional<UserUserInteraction> ofResult1 = Optional.of(userUserInteraction2);

        UserUserInteraction userUserInteraction3 = new UserUserInteraction();
        userUserInteraction3.setBanned(true);
        userUserInteraction3.setEmail("jane.doe@example.org");
        userUserInteraction3.setModerator(true);
        userUserInteraction3.setPassword("Pass123");
        userUserInteraction3.setid("42");
        userUserInteraction3.setusername("janedoe");
        Optional<UserUserInteraction> ofResult2 = Optional.of(userUserInteraction3);
        when(this.userRepository.save((UserUserInteraction) any())).thenReturn(userUserInteraction);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult);
        when(this.userRepository.findByUsername((String) any())).thenReturn(ofResult1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult2);

        UserUserInteraction userUserInteraction4 = new UserUserInteraction();
        userUserInteraction4.setBanned(true);
        userUserInteraction4.setEmail("jane.doe@example.org");
        userUserInteraction4.setModerator(true);
        userUserInteraction4.setPassword("Pass123");
        userUserInteraction4.setid("42");
        userUserInteraction4.setusername("janedoe");
        assertThrows(IllegalStateException.class, () -> this.userUserService.registerUser(userUserInteraction4));
        verify(this.userRepository).findByEmail((String) any());
    }

    /**
     * Method under test: {@link UserUserService#registerUser(UserUserInteraction)}
     */
    @Test
    void testRegisterUser2() {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("Pass123");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("Pass123");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction1);

        UserUserInteraction userUserInteraction2 = new UserUserInteraction();
        userUserInteraction2.setBanned(true);
        userUserInteraction2.setEmail("jane.doe@example.org");
        userUserInteraction2.setModerator(true);
        userUserInteraction2.setPassword("Pass123");
        userUserInteraction2.setid("42");
        userUserInteraction2.setusername("janedoe");
        Optional<UserUserInteraction> ofResult1 = Optional.of(userUserInteraction2);
        when(this.userRepository.save((UserUserInteraction) any())).thenReturn(userUserInteraction);
        when(this.userRepository.findByEmail((String) any())).thenReturn(Optional.empty());
        when(this.userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult1);

        UserUserInteraction userUserInteraction3 = new UserUserInteraction();
        userUserInteraction3.setBanned(true);
        userUserInteraction3.setEmail("jane.doe@example.org");
        userUserInteraction3.setModerator(true);
        userUserInteraction3.setPassword("Pass123");
        userUserInteraction3.setid("42");
        userUserInteraction3.setusername("janedoe");
        assertThrows(IllegalStateException.class, () -> this.userUserService.registerUser(userUserInteraction3));
        verify(this.userRepository).findByEmail((String) any());
        verify(this.userRepository).findByUsername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#registerUser(UserUserInteraction)}
     */
    @Test
    void testRegisterUser3() {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("Pass123");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("Pass123");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction1);

        UserUserInteraction userUserInteraction2 = new UserUserInteraction();
        userUserInteraction2.setBanned(true);
        userUserInteraction2.setEmail("jane.doe@example.org");
        userUserInteraction2.setModerator(true);
        userUserInteraction2.setPassword("Pass123");
        userUserInteraction2.setid("42");
        userUserInteraction2.setusername("janedoe");
        Optional<UserUserInteraction> ofResult1 = Optional.of(userUserInteraction2);

        UserUserInteraction userUserInteraction3 = new UserUserInteraction();
        userUserInteraction3.setBanned(true);
        userUserInteraction3.setEmail("jane.doe@example.org");
        userUserInteraction3.setModerator(true);
        userUserInteraction3.setPassword("Pass123");
        userUserInteraction3.setid("42");
        userUserInteraction3.setusername("janedoe");
        Optional<UserUserInteraction> ofResult2 = Optional.of(userUserInteraction3);
        when(this.userRepository.save((UserUserInteraction) any())).thenReturn(userUserInteraction);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult);
        when(this.userRepository.findByUsername((String) any())).thenReturn(ofResult1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult2);
        UserUserInteraction userUserInteraction4 = mock(UserUserInteraction.class);
        when(userUserInteraction4.getPassword()).thenReturn("Pass123");
        when(userUserInteraction4.getEmail()).thenReturn("jane.doe@example.org");
        when(userUserInteraction4.getusername()).thenReturn("janedoe");
        doNothing().when(userUserInteraction4).setBanned(anyBoolean());
        doNothing().when(userUserInteraction4).setEmail((String) any());
        doNothing().when(userUserInteraction4).setModerator(anyBoolean());
        doNothing().when(userUserInteraction4).setPassword((String) any());
        doNothing().when(userUserInteraction4).setid((String) any());
        doNothing().when(userUserInteraction4).setusername((String) any());
        userUserInteraction4.setBanned(true);
        userUserInteraction4.setEmail("jane.doe@example.org");
        userUserInteraction4.setModerator(true);
        userUserInteraction4.setPassword("Pass123");
        userUserInteraction4.setid("42");
        userUserInteraction4.setusername("janedoe");
        assertThrows(IllegalStateException.class, () -> this.userUserService.registerUser(userUserInteraction4));
        verify(this.userRepository).findByEmail((String) any());
        verify(userUserInteraction4, atLeast(1)).getEmail();
        verify(userUserInteraction4).getPassword();
        verify(userUserInteraction4).getusername();
        verify(userUserInteraction4).setBanned(anyBoolean());
        verify(userUserInteraction4).setEmail((String) any());
        verify(userUserInteraction4).setModerator(anyBoolean());
        verify(userUserInteraction4).setPassword((String) any());
        verify(userUserInteraction4).setid((String) any());
        verify(userUserInteraction4).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#registerUser(UserUserInteraction)}
     */
    @Test
    void testRegisterUser4() {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("Pass123");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("Pass123");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction1);

        UserUserInteraction userUserInteraction2 = new UserUserInteraction();
        userUserInteraction2.setBanned(true);
        userUserInteraction2.setEmail("jane.doe@example.org");
        userUserInteraction2.setModerator(true);
        userUserInteraction2.setPassword("Pass123");
        userUserInteraction2.setid("42");
        userUserInteraction2.setusername("janedoe");
        Optional<UserUserInteraction> ofResult1 = Optional.of(userUserInteraction2);

        UserUserInteraction userUserInteraction3 = new UserUserInteraction();
        userUserInteraction3.setBanned(true);
        userUserInteraction3.setEmail("jane.doe@example.org");
        userUserInteraction3.setModerator(true);
        userUserInteraction3.setPassword("Pass123");
        userUserInteraction3.setid("42");
        userUserInteraction3.setusername("janedoe");
        Optional<UserUserInteraction> ofResult2 = Optional.of(userUserInteraction3);
        when(this.userRepository.save((UserUserInteraction) any())).thenReturn(userUserInteraction);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult);
        when(this.userRepository.findByUsername((String) any())).thenReturn(ofResult1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult2);
        UserUserInteraction userUserInteraction4 = mock(UserUserInteraction.class);
        when(userUserInteraction4.getPassword()).thenThrow(new IllegalArgumentException("janedoe"));
        when(userUserInteraction4.getEmail()).thenReturn("jane.doe@example.org");
        when(userUserInteraction4.getusername()).thenReturn("janedoe");
        doNothing().when(userUserInteraction4).setBanned(anyBoolean());
        doNothing().when(userUserInteraction4).setEmail((String) any());
        doNothing().when(userUserInteraction4).setModerator(anyBoolean());
        doNothing().when(userUserInteraction4).setPassword((String) any());
        doNothing().when(userUserInteraction4).setid((String) any());
        doNothing().when(userUserInteraction4).setusername((String) any());
        userUserInteraction4.setBanned(true);
        userUserInteraction4.setEmail("jane.doe@example.org");
        userUserInteraction4.setModerator(true);
        userUserInteraction4.setPassword("Pass123");
        userUserInteraction4.setid("42");
        userUserInteraction4.setusername("janedoe");
        assertThrows(IllegalArgumentException.class, () -> this.userUserService.registerUser(userUserInteraction4));
        verify(userUserInteraction4).getEmail();
        verify(userUserInteraction4).getPassword();
        verify(userUserInteraction4).getusername();
        verify(userUserInteraction4).setBanned(anyBoolean());
        verify(userUserInteraction4).setEmail((String) any());
        verify(userUserInteraction4).setModerator(anyBoolean());
        verify(userUserInteraction4).setPassword((String) any());
        verify(userUserInteraction4).setid((String) any());
        verify(userUserInteraction4).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#registerUser(UserUserInteraction)}
     */
    @Test
    void testRegisterUser5() {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("Pass123");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("Pass123");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction1);

        UserUserInteraction userUserInteraction2 = new UserUserInteraction();
        userUserInteraction2.setBanned(true);
        userUserInteraction2.setEmail("jane.doe@example.org");
        userUserInteraction2.setModerator(true);
        userUserInteraction2.setPassword("Pass123");
        userUserInteraction2.setid("42");
        userUserInteraction2.setusername("janedoe");
        Optional<UserUserInteraction> ofResult1 = Optional.of(userUserInteraction2);
        when(this.userRepository.save((UserUserInteraction) any())).thenReturn(userUserInteraction);
        when(this.userRepository.findByEmail((String) any())).thenReturn(Optional.empty());
        when(this.userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult1);
        UserUserInteraction userUserInteraction3 = mock(UserUserInteraction.class);
        when(userUserInteraction3.getPassword()).thenReturn("Pass123");
        when(userUserInteraction3.getEmail()).thenReturn("jane.doe@example.org");
        when(userUserInteraction3.getusername()).thenReturn("janedoe");
        doNothing().when(userUserInteraction3).setBanned(anyBoolean());
        doNothing().when(userUserInteraction3).setEmail((String) any());
        doNothing().when(userUserInteraction3).setModerator(anyBoolean());
        doNothing().when(userUserInteraction3).setPassword((String) any());
        doNothing().when(userUserInteraction3).setid((String) any());
        doNothing().when(userUserInteraction3).setusername((String) any());
        userUserInteraction3.setBanned(true);
        userUserInteraction3.setEmail("jane.doe@example.org");
        userUserInteraction3.setModerator(true);
        userUserInteraction3.setPassword("Pass123");
        userUserInteraction3.setid("42");
        userUserInteraction3.setusername("janedoe");
        assertThrows(IllegalStateException.class, () -> this.userUserService.registerUser(userUserInteraction3));
        verify(this.userRepository).findByEmail((String) any());
        verify(this.userRepository).findByUsername((String) any());
        verify(userUserInteraction3, atLeast(1)).getEmail();
        verify(userUserInteraction3).getPassword();
        verify(userUserInteraction3, atLeast(1)).getusername();
        verify(userUserInteraction3).setBanned(anyBoolean());
        verify(userUserInteraction3).setEmail((String) any());
        verify(userUserInteraction3).setModerator(anyBoolean());
        verify(userUserInteraction3).setPassword((String) any());
        verify(userUserInteraction3).setid((String) any());
        verify(userUserInteraction3).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#updateUser(String, String, String, String, String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testUpdateUser() throws IOException {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class,
                () -> this.userUserService.updateUser("42", "janedoe", "jane.doe@example.org", "pass", "pass",
                        new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(this.userRepository).findById((String) any());
    }

    /**
     * Method under test: {@link UserUserService#updateUser(String, String, String, String, String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testUpdateUser2() throws IOException {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.getPassword()).thenReturn("pass");
        when(userUserInteraction.getEmail()).thenReturn("jane.doe@example.org");
        when(userUserInteraction.getusername()).thenReturn("janedoe");
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        Optional<UserUserInteraction> ofResult1 = Optional.of(userUserInteraction1);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class,
                () -> this.userUserService.updateUser("42", "janedoe", "jane.doe@example.org", "pass", "pass",
                        new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction).getEmail();
        verify(userUserInteraction).getPassword();
        verify(userUserInteraction).getusername();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#updateUser(String, String, String, String, String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testUpdateUser3() throws IOException {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.getPassword()).thenThrow(new IllegalArgumentException("Old Password is incorrect."));
        when(userUserInteraction.getEmail()).thenReturn("jane.doe@example.org");
        when(userUserInteraction.getusername()).thenReturn("janedoe");
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        Optional<UserUserInteraction> ofResult1 = Optional.of(userUserInteraction1);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalArgumentException.class,
                () -> this.userUserService.updateUser("42", "janedoe", "jane.doe@example.org", "pass", "pass",
                        new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction).getEmail();
        verify(userUserInteraction).getPassword();
        verify(userUserInteraction).getusername();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#updateUser(String, String, String, String, String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testUpdateUser4() throws IOException {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.getPassword()).thenReturn("pass");
        when(userUserInteraction.getEmail()).thenReturn("Old Password is incorrect.");
        when(userUserInteraction.getusername()).thenReturn("janedoe");
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        Optional<UserUserInteraction> ofResult1 = Optional.of(userUserInteraction1);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class,
                () -> this.userUserService.updateUser("42", "janedoe", "jane.doe@example.org", "pass", "pass",
                        new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(this.userRepository).findByEmail((String) any());
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction).getEmail();
        verify(userUserInteraction).getusername();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#updateUser(String, String, String, String, String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testUpdateUser5() throws IOException {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.getPassword()).thenReturn("pass");
        when(userUserInteraction.getEmail()).thenReturn("jane.doe@example.org");
        when(userUserInteraction.getusername()).thenReturn("Old Password is incorrect.");
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        Optional<UserUserInteraction> ofResult1 = Optional.of(userUserInteraction1);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class,
                () -> this.userUserService.updateUser("42", "janedoe", "jane.doe@example.org", "pass", "pass",
                        new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction).getEmail();
        verify(userUserInteraction).getPassword();
        verify(userUserInteraction).getusername();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction, atLeast(1)).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#updateUser(String, String, String, String, String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateUser6() throws IOException {
        // TODO: Complete this test.

        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult);
        when(this.userRepository.findById((String) any())).thenReturn(null);
        UserUserInteraction userUserInteraction1 = mock(UserUserInteraction.class);
        when(userUserInteraction1.getPassword()).thenReturn("pass");
        when(userUserInteraction1.getEmail()).thenReturn("jane.doe@example.org");
        when(userUserInteraction1.getusername()).thenReturn("janedoe");
        doNothing().when(userUserInteraction1).setBanned(anyBoolean());
        doNothing().when(userUserInteraction1).setEmail((String) any());
        doNothing().when(userUserInteraction1).setModerator(anyBoolean());
        doNothing().when(userUserInteraction1).setPassword((String) any());
        doNothing().when(userUserInteraction1).setid((String) any());
        doNothing().when(userUserInteraction1).setusername((String) any());
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        this.userUserService.updateUser("42", "janedoe", "jane.doe@example.org", "pass", "pass",
                new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8"))));
    }

    /**
     * Method under test: {@link UserUserService#updateUser(String, String, String, String, String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testUpdateUser7() throws IOException {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult);
        when(this.userRepository.findById((String) any())).thenReturn(Optional.empty());
        UserUserInteraction userUserInteraction1 = mock(UserUserInteraction.class);
        when(userUserInteraction1.getPassword()).thenReturn("pass");
        when(userUserInteraction1.getEmail()).thenReturn("jane.doe@example.org");
        when(userUserInteraction1.getusername()).thenReturn("janedoe");
        doNothing().when(userUserInteraction1).setBanned(anyBoolean());
        doNothing().when(userUserInteraction1).setEmail((String) any());
        doNothing().when(userUserInteraction1).setModerator(anyBoolean());
        doNothing().when(userUserInteraction1).setPassword((String) any());
        doNothing().when(userUserInteraction1).setid((String) any());
        doNothing().when(userUserInteraction1).setusername((String) any());
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        assertThrows(IllegalStateException.class,
                () -> this.userUserService.updateUser("42", "janedoe", "jane.doe@example.org", "pass", "pass",
                        new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction1).setBanned(anyBoolean());
        verify(userUserInteraction1).setEmail((String) any());
        verify(userUserInteraction1).setModerator(anyBoolean());
        verify(userUserInteraction1).setPassword((String) any());
        verify(userUserInteraction1).setid((String) any());
        verify(userUserInteraction1).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#updateUser(String, String, String, String, String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testUpdateUser8() throws IOException {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.getPassword()).thenReturn("pass");
        when(userUserInteraction.getEmail()).thenReturn("jane.doe@example.org");
        when(userUserInteraction.getusername()).thenReturn("janedoe");
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        Optional<UserUserInteraction> ofResult1 = Optional.of(userUserInteraction1);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class, () -> this.userUserService.updateUser("42", null, "jane.doe@example.org",
                "pass", "pass", new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction).getEmail();
        verify(userUserInteraction).getPassword();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#updateUser(String, String, String, String, String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testUpdateUser9() throws IOException {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.getPassword()).thenReturn("pass");
        when(userUserInteraction.getEmail()).thenReturn("jane.doe@example.org");
        when(userUserInteraction.getusername()).thenReturn("janedoe");
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        Optional<UserUserInteraction> ofResult1 = Optional.of(userUserInteraction1);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class, () -> this.userUserService.updateUser("42", "", "jane.doe@example.org",
                "pass", "pass", new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction).getEmail();
        verify(userUserInteraction).getPassword();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#updateUser(String, String, String, String, String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testUpdateUser10() throws IOException {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.getPassword()).thenReturn("pass");
        when(userUserInteraction.getEmail()).thenReturn("jane.doe@example.org");
        when(userUserInteraction.getusername()).thenReturn("janedoe");
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        Optional<UserUserInteraction> ofResult1 = Optional.of(userUserInteraction1);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class, () -> this.userUserService.updateUser("42", "janedoe", null, "pass",
                "pass", new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction).getPassword();
        verify(userUserInteraction).getusername();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#updateUser(String, String, String, String, String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testUpdateUser11() throws IOException {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.getPassword()).thenReturn("pass");
        when(userUserInteraction.getEmail()).thenReturn("jane.doe@example.org");
        when(userUserInteraction.getusername()).thenReturn("janedoe");
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        Optional<UserUserInteraction> ofResult1 = Optional.of(userUserInteraction1);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class, () -> this.userUserService.updateUser("42", "janedoe", "", "pass",
                "pass", new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction).getPassword();
        verify(userUserInteraction).getusername();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#updateUser(String, String, String, String, String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testUpdateUser12() throws IOException {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.getPassword()).thenReturn("pass");
        when(userUserInteraction.getEmail()).thenReturn("jane.doe@example.org");
        when(userUserInteraction.getusername()).thenReturn("janedoe");
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        Optional<UserUserInteraction> ofResult1 = Optional.of(userUserInteraction1);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class,
                () -> this.userUserService.updateUser("42", "janedoe", "jane.doe@example.org", null, "pass",
                        new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction).getEmail();
        verify(userUserInteraction).getusername();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#updateUser(String, String, String, String, String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testUpdateUser13() throws IOException {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.getPassword()).thenReturn("pass");
        when(userUserInteraction.getEmail()).thenReturn("jane.doe@example.org");
        when(userUserInteraction.getusername()).thenReturn("janedoe");
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        Optional<UserUserInteraction> ofResult1 = Optional.of(userUserInteraction1);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class,
                () -> this.userUserService.updateUser("42", "janedoe", "jane.doe@example.org", "", "pass",
                        new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction).getEmail();
        verify(userUserInteraction).getusername();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#deleteProfilePicture(String)}
     */
    @Test
    void testDeleteProfilePicture() throws IOException {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class, () -> this.userUserService.deleteProfilePicture("42"));
        verify(this.userRepository).findById((String) any());
    }

    /**
     * Method under test: {@link UserUserService#deleteProfilePicture(String)}
     */
    @Test
    void testDeleteProfilePicture2() throws IOException {
        UserUserInteraction userUserInteraction = new UserUserInteraction("janedoe", "pass", "jane.doe@example.org",
                "No photo to delete.", true, true);
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        doNothing().when(this.firebaseImageService).delete((String) any());
        assertEquals("Profile picture deleted successfully", this.userUserService.deleteProfilePicture("42"));
        verify(this.userRepository).findById((String) any());
        verify(this.firebaseImageService).delete((String) any());
    }

    /**
     * Method under test: {@link UserUserService#deleteProfilePicture(String)}
     */
    @Test
    void testDeleteProfilePicture3() throws IOException {
        UserUserInteraction userUserInteraction = new UserUserInteraction("janedoe", "pass", "jane.doe@example.org", "",
                true, true);
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        doNothing().when(this.firebaseImageService).delete((String) any());
        assertThrows(IllegalStateException.class, () -> this.userUserService.deleteProfilePicture("42"));
        verify(this.userRepository).findById((String) any());
    }

    /**
     * Method under test: {@link UserUserService#deleteProfilePicture(String)}
     */
    @Test
    void testDeleteProfilePicture4() throws IOException {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        doNothing().when(userUserInteraction).setPhotoRef((String) any());
        when(userUserInteraction.getPhotoRef()).thenReturn("Photo Ref");
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        doNothing().when(this.firebaseImageService).delete((String) any());
        assertEquals("Profile picture deleted successfully", this.userUserService.deleteProfilePicture("42"));
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction).getPhotoRef();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setPhotoRef((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
        verify(this.firebaseImageService).delete((String) any());
    }

    /**
     * Method under test: {@link UserUserService#deleteProfilePicture(String)}
     */
    @Test
    void testDeleteProfilePicture5() throws IOException {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        doThrow(new IllegalArgumentException("Profile picture deleted successfully")).when(userUserInteraction)
                .setPhotoRef((String) any());
        when(userUserInteraction.getPhotoRef()).thenReturn("Photo Ref");
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        doNothing().when(this.firebaseImageService).delete((String) any());
        assertThrows(IllegalArgumentException.class, () -> this.userUserService.deleteProfilePicture("42"));
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction).getPhotoRef();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setPhotoRef((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
        verify(this.firebaseImageService).delete((String) any());
    }

    /**
     * Method under test: {@link UserUserService#deleteProfilePicture(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteProfilePicture6() throws IOException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at guc.bttsBtngan.user.services.UserUserService.deleteProfilePicture(UserUserService.java:186)
        //   In order to prevent deleteProfilePicture(String)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   deleteProfilePicture(String).
        //   See https://diff.blue/R013 to resolve this issue.

        when(this.userRepository.findById((String) any())).thenReturn(null);
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        doNothing().when(userUserInteraction).setPhotoRef((String) any());
        when(userUserInteraction.getPhotoRef()).thenReturn("Photo Ref");
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        doNothing().when(this.firebaseImageService).delete((String) any());
        this.userUserService.deleteProfilePicture("42");
    }

    /**
     * Method under test: {@link UserUserService#deleteProfilePicture(String)}
     */
    @Test
    void testDeleteProfilePicture7() throws IOException {
        when(this.userRepository.findById((String) any())).thenReturn(Optional.empty());
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        doNothing().when(userUserInteraction).setPhotoRef((String) any());
        when(userUserInteraction.getPhotoRef()).thenReturn("Photo Ref");
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        doNothing().when(this.firebaseImageService).delete((String) any());
        assertThrows(IllegalStateException.class, () -> this.userUserService.deleteProfilePicture("42"));
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#getAllphotoRef(String)}
     */
    @Test
    void testGetAllphotoRef() {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findByphotoRef((String) any())).thenReturn(ofResult);
        assertEquals(
                "Optional[UserUserInteraction{id='42', username='janedoe', Password='pass', email='jane.doe@example.org',"
                        + " photoRef='null', isModerator=true, isBanned=true}]",
                this.userUserService.getAllphotoRef("Photo Ref"));
        verify(this.userRepository, atLeast(1)).findByphotoRef((String) any());
    }

    /**
     * Method under test: {@link UserUserService#getAllphotoRef(String)}
     */
    @Test
    void testGetAllphotoRef2() {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findByphotoRef((String) any())).thenReturn(ofResult);
        this.userUserService.getAllphotoRef("Photo Ref");
        verify(this.userRepository, atLeast(1)).findByphotoRef((String) any());
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#banUser(String, String)}
     */
    @Test
    void testBanUser() {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class, () -> this.userUserService.banUser("42", "42"));
        verify(this.userRepository).findById((String) any());
    }

    /**
     * Method under test: {@link UserUserService#banUser(String, String)}
     */
    @Test
    void testBanUser2() {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.isBanned()).thenReturn(true);
        when(userUserInteraction.isModerator()).thenReturn(true);
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class, () -> this.userUserService.banUser("42", "42"));
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction).isBanned();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#banUser(String, String)}
     */
    @Test
    void testBanUser3() {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.isBanned()).thenReturn(false);
        when(userUserInteraction.isModerator()).thenReturn(true);
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class, () -> this.userUserService.banUser("42", "42"));
        verify(this.userRepository, atLeast(1)).findById((String) any());
        verify(userUserInteraction).isBanned();
        verify(userUserInteraction).isModerator();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#banUser(String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testBanUser4() {
        // TODO: Complete this test.

        when(this.userRepository.findById((String) any())).thenReturn(Optional.empty());
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.isBanned()).thenReturn(true);
        when(userUserInteraction.isModerator()).thenReturn(true);
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        this.userUserService.banUser("42", "42");
    }

    /**
     * Method under test: {@link UserUserService#unbanUser(String, String)}
     */
    @Test
    void testUnbanUser() {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        when(this.userRepository.save((UserUserInteraction) any())).thenReturn(userUserInteraction1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertEquals("User unbanned successfully", this.userUserService.unbanUser("42", "42"));
        verify(this.userRepository).save((UserUserInteraction) any());
        verify(this.userRepository, atLeast(1)).findById((String) any());
    }

    /**
     * Method under test: {@link UserUserService#unbanUser(String, String)}
     */
    @Test
    void testUnbanUser2() {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.save((UserUserInteraction) any()))
                .thenThrow(new IllegalArgumentException("User unbanned successfully"));
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalArgumentException.class, () -> this.userUserService.unbanUser("42", "42"));
        verify(this.userRepository).save((UserUserInteraction) any());
        verify(this.userRepository, atLeast(1)).findById((String) any());
    }

    /**
     * Method under test: {@link UserUserService#unbanUser(String, String)}
     */
    @Test
    void testUnbanUser3() {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.isModerator()).thenReturn(true);
        when(userUserInteraction.isBanned()).thenReturn(true);
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        when(this.userRepository.save((UserUserInteraction) any())).thenReturn(userUserInteraction1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertEquals("User unbanned successfully", this.userUserService.unbanUser("42", "42"));
        verify(this.userRepository).save((UserUserInteraction) any());
        verify(this.userRepository, atLeast(1)).findById((String) any());
        verify(userUserInteraction).isBanned();
        verify(userUserInteraction).isModerator();
        verify(userUserInteraction, atLeast(1)).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#unbanUser(String, String)}
     */
    @Test
    void testUnbanUser4() {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.isModerator())
                .thenThrow(new IllegalArgumentException("Unauthorized, you are not a moderator!"));
        when(userUserInteraction.isBanned()).thenReturn(true);
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        when(this.userRepository.save((UserUserInteraction) any())).thenReturn(userUserInteraction1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalArgumentException.class, () -> this.userUserService.unbanUser("42", "42"));
        verify(this.userRepository, atLeast(1)).findById((String) any());
        verify(userUserInteraction).isBanned();
        verify(userUserInteraction).isModerator();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#unbanUser(String, String)}
     */
    @Test
    void testUnbanUser5() {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.isModerator()).thenReturn(false);
        when(userUserInteraction.isBanned()).thenReturn(true);
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        when(this.userRepository.save((UserUserInteraction) any())).thenReturn(userUserInteraction1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class, () -> this.userUserService.unbanUser("42", "42"));
        verify(this.userRepository, atLeast(1)).findById((String) any());
        verify(userUserInteraction).isBanned();
        verify(userUserInteraction).isModerator();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#unbanUser(String, String)}
     */
    @Test
    void testUnbanUser6() {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.isModerator()).thenReturn(true);
        when(userUserInteraction.isBanned()).thenReturn(false);
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);

        UserUserInteraction userUserInteraction1 = new UserUserInteraction();
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        when(this.userRepository.save((UserUserInteraction) any())).thenReturn(userUserInteraction1);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class, () -> this.userUserService.unbanUser("42", "42"));
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction).isBanned();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
    }

    /**
     * Method under test: {@link UserUserService#unbanUser(String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUnbanUser7() {
        // TODO: Complete this test.

        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        when(this.userRepository.save((UserUserInteraction) any())).thenReturn(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(Optional.empty());
        UserUserInteraction userUserInteraction1 = mock(UserUserInteraction.class);
        when(userUserInteraction1.isModerator()).thenReturn(true);
        when(userUserInteraction1.isBanned()).thenReturn(true);
        doNothing().when(userUserInteraction1).setBanned(anyBoolean());
        doNothing().when(userUserInteraction1).setEmail((String) any());
        doNothing().when(userUserInteraction1).setModerator(anyBoolean());
        doNothing().when(userUserInteraction1).setPassword((String) any());
        doNothing().when(userUserInteraction1).setid((String) any());
        doNothing().when(userUserInteraction1).setusername((String) any());
        userUserInteraction1.setBanned(true);
        userUserInteraction1.setEmail("jane.doe@example.org");
        userUserInteraction1.setModerator(true);
        userUserInteraction1.setPassword("pass");
        userUserInteraction1.setid("42");
        userUserInteraction1.setusername("janedoe");
        this.userUserService.unbanUser("42", "42");
    }

    /**
     * Method under test: {@link UserUserService#DeleteUser(String)}
     */
    @Test
    void testDeleteUser() throws Exception {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        doNothing().when(this.userRepository).deleteById((String) any());
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);

        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.remove((org.springframework.data.mongodb.core.query.Query) any(), (Class<Object>) any()))
                .thenReturn(null);
        assertEquals("User account has been successfully deleted", this.userUserService.DeleteUser("42"));
        verify(this.userRepository).findById((String) any());
        verify(this.userRepository).deleteById((String) any());
        verify(this.userPostRepository).findByUserId((String) any());
        verify(this.mongoOperations).remove((org.springframework.data.mongodb.core.query.Query) any(),
                (Class<Object>) any());
    }

    /**
     * Method under test: {@link UserUserService#DeleteUser(String)}
     */
    @Test
    void testDeleteUser2() throws Exception {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        doNothing().when(this.userRepository).deleteById((String) any());
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);

        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.remove((org.springframework.data.mongodb.core.query.Query) any(), (Class<Object>) any()))
                .thenThrow(new IllegalArgumentException("user:  "));
        assertThrows(IllegalArgumentException.class, () -> this.userUserService.DeleteUser("42"));
        verify(this.userRepository).findById((String) any());
        verify(this.userPostRepository).findByUserId((String) any());
        verify(this.mongoOperations).remove((org.springframework.data.mongodb.core.query.Query) any(),
                (Class<Object>) any());
    }

    /**
     * Method under test: {@link UserUserService#DeleteUser(String)}
     */
    @Test
    void testDeleteUser3() throws Exception {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        doNothing().when(this.userRepository).deleteById((String) any());
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);

        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.remove((org.springframework.data.mongodb.core.query.Query) any(), (Class<Object>) any()))
                .thenReturn(null);
        assertEquals("User account has been successfully deleted", this.userUserService.DeleteUser("42"));
        verify(this.userRepository).findById((String) any());
        verify(this.userRepository).deleteById((String) any());
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
        verify(this.userPostRepository).findByUserId((String) any());
        verify(this.mongoOperations).remove((org.springframework.data.mongodb.core.query.Query) any(),
                (Class<Object>) any());
    }

    /**
     * Method under test: {@link UserUserService#DeleteUser(String)}
     */
    @Test
    void testDeleteUser4() throws Exception {
        doNothing().when(this.userRepository).deleteById((String) any());
        when(this.userRepository.findById((String) any())).thenReturn(Optional.empty());
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");

        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.remove((org.springframework.data.mongodb.core.query.Query) any(), (Class<Object>) any()))
                .thenReturn(null);
        assertThrows(IllegalStateException.class, () -> this.userUserService.DeleteUser("42"));
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
        verify(this.userPostRepository).findByUserId((String) any());
    }

    /**
     * Method under test: {@link UserUserService#DeleteUser(String)}
     */
    @Test
    void testDeleteUser5() throws Exception {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        doNothing().when(this.userRepository).deleteById((String) any());
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);

        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("user:  ");

        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(stringList);
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.remove((org.springframework.data.mongodb.core.query.Query) any(), (Class<Object>) any()))
                .thenReturn(null);
        assertEquals("User account has been successfully deleted", this.userUserService.DeleteUser("42"));
        verify(this.userRepository).findById((String) any());
        verify(this.userRepository).deleteById((String) any());
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
        verify(this.userPostRepository, atLeast(1)).findByUserId((String) any());
        verify(this.mongoOperations).remove((org.springframework.data.mongodb.core.query.Query) any(),
                (Class<Object>) any());
    }

    /**
     * Method under test: {@link UserUserService#DeleteUser(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteUser6() throws Exception {
        // TODO: Complete this test.

        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("pass");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        doNothing().when(this.userRepository).deleteById((String) any());
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);

        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("user:  ");

        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(stringList);
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.remove((org.springframework.data.mongodb.core.query.Query) any(), (Class<Object>) any()))
                .thenReturn(null);
        this.userUserService.DeleteUser("42");
    }

    /**
     * Method under test: {@link UserUserService#notify(ArrayList)}
     */
    @Test
    void testNotify() throws AmqpException {
        doNothing().when(this.amqpTemplate)
                .convertAndSend((String) any(), (Object) any(), (org.springframework.amqp.core.MessagePostProcessor) any());
        this.userUserService.notify(new ArrayList<>());
        verify(this.amqpTemplate).convertAndSend((String) any(), (Object) any(),
                (org.springframework.amqp.core.MessagePostProcessor) any());
    }

    /**
     * Method under test: {@link UserUserService#notify(ArrayList)}
     */
    @Test
    void testNotify2() throws AmqpException {
        doThrow(new IllegalArgumentException("type")).when(this.amqpTemplate)
                .convertAndSend((String) any(), (Object) any(), (org.springframework.amqp.core.MessagePostProcessor) any());
        assertThrows(IllegalArgumentException.class, () -> this.userUserService.notify(new ArrayList<>()));
        verify(this.amqpTemplate).convertAndSend((String) any(), (Object) any(),
                (org.springframework.amqp.core.MessagePostProcessor) any());
    }

    /**
     * Method under test: {@link UserUserService#followUser(String, String)}
     */
    @Test
    void testFollowUser() throws AmqpException {
        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any())).thenReturn(null);
        doNothing().when(this.amqpTemplate)
                .convertAndSend((String) any(), (Object) any(), (org.springframework.amqp.core.MessagePostProcessor) any());
        assertEquals("you are following this user now", this.userUserService.followUser("42", "42"));
        verify(this.userPostRepository, atLeast(1)).findByUserId((String) any());
        verify(this.mongoOperations, atLeast(1)).updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any());
        verify(this.amqpTemplate).convertAndSend((String) any(), (Object) any(),
                (org.springframework.amqp.core.MessagePostProcessor) any());
    }

    /**
     * Method under test: {@link UserUserService#followUser(String, String)}
     */
    @Test
    void testFollowUser2() throws AmqpException {
        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any())).thenReturn(null);
        doThrow(new IllegalArgumentException("userId: %s")).when(this.amqpTemplate)
                .convertAndSend((String) any(), (Object) any(), (org.springframework.amqp.core.MessagePostProcessor) any());
        assertThrows(IllegalArgumentException.class, () -> this.userUserService.followUser("42", "42"));
        verify(this.userPostRepository, atLeast(1)).findByUserId((String) any());
        verify(this.mongoOperations, atLeast(1)).updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any());
        verify(this.amqpTemplate).convertAndSend((String) any(), (Object) any(),
                (org.springframework.amqp.core.MessagePostProcessor) any());
    }

    /**
     * Method under test: {@link UserUserService#unfollowUser(String, String)}
     */
    @Test
    void testUnfollowUser() {
        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any())).thenReturn(null);
        assertEquals("you are not following this user anymore", this.userUserService.unfollowUser("42", "42"));
        verify(this.userPostRepository, atLeast(1)).findByUserId((String) any());
        verify(this.mongoOperations, atLeast(1)).updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any());
    }

    /**
     * Method under test: {@link UserUserService#unfollowUser(String, String)}
     */
    @Test
    void testUnfollowUser2() {
        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any()))
                .thenThrow(new IllegalArgumentException("userId"));
        assertThrows(IllegalArgumentException.class, () -> this.userUserService.unfollowUser("42", "42"));
        verify(this.userPostRepository, atLeast(1)).findByUserId((String) any());
        verify(this.mongoOperations).updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any());
    }
}

