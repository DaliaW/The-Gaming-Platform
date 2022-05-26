package guc.bttsBtngan.user.services;

import guc.bttsBtngan.user.data.UserPostInteraction;
import guc.bttsBtngan.user.data.UserReports;
import guc.bttsBtngan.user.data.UserUserInteraction;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserPostService.class})
@ExtendWith(SpringExtension.class)
class UserPostServiceTest {
    @MockBean
    private MongoOperations mongoOperations;

    @MockBean
    private UserPostRepository userPostRepository;

    @Autowired
    private UserPostService userPostService;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link UserPostService#getAllReports(String)}
     */
    @Test
    void testGetAllReports() throws Exception {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("iloveyou");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        when(this.mongoOperations.findAll((Class<UserReports>) any())).thenReturn(new ArrayList<>());
        assertEquals("[]", this.userPostService.getAllReports("42"));
        verify(this.userRepository).findById((String) any());
        verify(this.mongoOperations).findAll((Class<UserReports>) any());
    }

    /**
     * Method under test: {@link UserPostService#getAllReports(String)}
     */
    @Test
    void testGetAllReports2() throws Exception {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("iloveyou");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        when(this.mongoOperations.findAll((Class<UserReports>) any())).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.userPostService.getAllReports("42"));
        verify(this.userRepository).findById((String) any());
        verify(this.mongoOperations).findAll((Class<UserReports>) any());
    }

    /**
     * Method under test: {@link UserPostService#getAllReports(String)}
     */
    @Test
    void testGetAllReports3() throws Exception {
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.isModerator()).thenReturn(false);
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("iloveyou");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        when(this.mongoOperations.findAll((Class<Object>) any())).thenReturn(new ArrayList<>());
        when(this.mongoOperations.findAll((Class<UserReports>) any())).thenReturn(new ArrayList<>());
        assertThrows(IllegalStateException.class, () -> this.userPostService.getAllReports("42"));
        verify(this.userRepository).findById((String) any());
        verify(userUserInteraction).isModerator();
        verify(userUserInteraction).setBanned(anyBoolean());
        verify(userUserInteraction).setEmail((String) any());
        verify(userUserInteraction).setModerator(anyBoolean());
        verify(userUserInteraction).setPassword((String) any());
        verify(userUserInteraction).setid((String) any());
        verify(userUserInteraction).setusername((String) any());
        verify(this.mongoOperations).findAll((Class<Object>) any());
    }

    /**
     * Method under test: {@link UserPostService#getAllReports(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllReports4() throws Exception {
        // TODO: Complete this test.
        when(this.userRepository.findById((String) any())).thenReturn(Optional.empty());
        UserUserInteraction userUserInteraction = mock(UserUserInteraction.class);
        when(userUserInteraction.isModerator()).thenReturn(false);
        doNothing().when(userUserInteraction).setBanned(anyBoolean());
        doNothing().when(userUserInteraction).setEmail((String) any());
        doNothing().when(userUserInteraction).setModerator(anyBoolean());
        doNothing().when(userUserInteraction).setPassword((String) any());
        doNothing().when(userUserInteraction).setid((String) any());
        doNothing().when(userUserInteraction).setusername((String) any());
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("iloveyou");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        when(this.mongoOperations.findAll((Class<Object>) any())).thenReturn(new ArrayList<>());
        when(this.mongoOperations.findAll((Class<UserReports>) any())).thenReturn(new ArrayList<>());
        this.userPostService.getAllReports("42");
    }

    /**
     * Method under test: {@link UserPostService#reportUser(String, String, String)}
     */
    @Test
    void testReportUser() throws Exception {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("iloveyou");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);

        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any())).thenReturn(null);
        assertEquals(" the report has been posted successfully: ",
                this.userPostService.reportUser("42", "User Id2", "Report Comment"));
        verify(this.userRepository, atLeast(1)).findById((String) any());
        verify(this.userPostRepository).findByUserId((String) any());
        verify(this.mongoOperations).updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any());
    }

    /**
     * Method under test: {@link UserPostService#reportUser(String, String, String)}
     */
    @Test
    void testReportUser2() throws Exception {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("iloveyou");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);

        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any()))
                .thenThrow(new IllegalStateException("here is the data "));
        assertThrows(IllegalStateException.class,
                () -> this.userPostService.reportUser("42", "User Id2", "Report Comment"));
        verify(this.userRepository, atLeast(1)).findById((String) any());
        verify(this.userPostRepository).findByUserId((String) any());
        verify(this.mongoOperations).updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any());
    }

    /**
     * Method under test: {@link UserPostService#reportUser(String, String, String)}
     */
    @Test
    void testReportUser3() throws Exception {
        when(this.userRepository.findById((String) any())).thenReturn(Optional.empty());

        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any())).thenReturn(null);
        assertThrows(Exception.class, () -> this.userPostService.reportUser("42", "User Id2", "Report Comment"));
        verify(this.userRepository).findById((String) any());
        verify(this.userPostRepository).findByUserId((String) any());
    }

    /**
     * Method under test: {@link UserPostService#reportUser(String, String, String)}
     */
    @Test
    void testReportUser4() throws Exception {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("iloveyou");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);

        ArrayList<UserReports> userReportsList = new ArrayList<>();
        userReportsList.add(new UserReports("42", "here is the data "));

        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(userReportsList);
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any())).thenReturn(null);
        assertEquals(" the report has been posted successfully: ",
                this.userPostService.reportUser("42", "User Id2", "Report Comment"));
        verify(this.userRepository, atLeast(1)).findById((String) any());
        verify(this.userPostRepository).findByUserId((String) any());
        verify(this.mongoOperations).updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any());
    }

    /**
     * Method under test: {@link UserPostService#reportUser(String, String, String)}
     */
    @Test
    void testReportUser5() throws Exception {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("iloveyou");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);

        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any())).thenReturn(null);
        assertEquals(" the report has been posted successfully: ", this.userPostService.reportUser("42", "User Id2", ""));
        verify(this.userRepository, atLeast(1)).findById((String) any());
        verify(this.userPostRepository).findByUserId((String) any());
        verify(this.mongoOperations).updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any());
    }

    /**
     * Method under test: {@link UserPostService#blockUser(String, String)}
     */
    @Test
    void testBlockUser() throws Exception {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("iloveyou");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);

        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any())).thenReturn(null);
        assertEquals("User is blocked", this.userPostService.blockUser("42", "42"));
        verify(this.userRepository).findById((String) any());
        verify(this.userPostRepository, atLeast(1)).findByUserId((String) any());
        verify(this.mongoOperations, atLeast(1)).updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any());
    }

    /**
     * Method under test: {@link UserPostService#blockUser(String, String)}
     */
    @Test
    void testBlockUser2() throws Exception {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("iloveyou");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);

        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any()))
                .thenThrow(new IllegalStateException("userId"));
        assertThrows(IllegalStateException.class, () -> this.userPostService.blockUser("42", "42"));
        verify(this.userRepository).findById((String) any());
        verify(this.userPostRepository, atLeast(1)).findByUserId((String) any());
        verify(this.mongoOperations).updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any());
    }

    /**
     * Method under test: {@link UserPostService#blockUser(String, String)}
     */
    @Test
    void testBlockUser3() throws Exception {
        when(this.userRepository.findById((String) any())).thenReturn(Optional.empty());

        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        when(this.mongoOperations.updateFirst((org.springframework.data.mongodb.core.query.Query) any(),
                (org.springframework.data.mongodb.core.query.UpdateDefinition) any(), (Class<Object>) any())).thenReturn(null);
        assertThrows(Exception.class, () -> this.userPostService.blockUser("42", "42"));
        verify(this.userRepository).findById((String) any());
        verify(this.userPostRepository).findByUserId((String) any());
    }

    /**
     * Method under test: {@link UserPostService#unblockUser(String, String)}
     */
    @Test
    void testUnblockUser() throws Exception {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("iloveyou");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);

        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        assertThrows(Exception.class, () -> this.userPostService.unblockUser("42", "42"));
        verify(this.userRepository).findById((String) any());
        verify(this.userPostRepository).findByUserId((String) any());
    }

    /**
     * Method under test: {@link UserPostService#unblockUser(String, String)}
     */
    @Test
    void testUnblockUser2() throws Exception {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("iloveyou");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        when(this.userPostRepository.findByUserId((String) any())).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.userPostService.unblockUser("42", "42"));
        verify(this.userRepository).findById((String) any());
        verify(this.userPostRepository).findByUserId((String) any());
    }

    /**
     * Method under test: {@link UserPostService#unblockUser(String, String)}
     */
    @Test
    void testUnblockUser3() throws Exception {
        when(this.userRepository.findById((String) any())).thenReturn(Optional.empty());

        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        assertThrows(Exception.class, () -> this.userPostService.unblockUser("42", "42"));
        verify(this.userRepository).findById((String) any());
    }

    /**
     * Method under test: {@link UserPostService#userRecommendations(String)}
     */
    @Test
    void testUserRecommendations() {
        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        assertTrue(this.userPostService.userRecommendations("42").isEmpty());
        verify(this.userPostRepository).findByUserId((String) any());
    }

    /**
     * Method under test: {@link UserPostService#userRecommendations(String)}
     */
    @Test
    void testUserRecommendations2() {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("foo");

        UserPostInteraction userPostInteraction = new UserPostInteraction();
        userPostInteraction.setBlockedBy(new ArrayList<>());
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(stringList);
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        assertTrue(this.userPostService.userRecommendations("42").isEmpty());
        verify(this.userPostRepository, atLeast(1)).findByUserId((String) any());
    }

    /**
     * Method under test: {@link UserPostService#userRecommendations(String)}
     */
    @Test
    void testUserRecommendations3() {
        when(this.userPostRepository.findByUserId((String) any())).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.userPostService.userRecommendations("42"));
        verify(this.userPostRepository).findByUserId((String) any());
    }

    /**
     * Method under test: {@link UserPostService#validate(String)}
     */
    @Test
    void testValidate() {
        UserUserInteraction userUserInteraction = new UserUserInteraction();
        userUserInteraction.setBanned(true);
        userUserInteraction.setEmail("jane.doe@example.org");
        userUserInteraction.setModerator(true);
        userUserInteraction.setPassword("iloveyou");
        userUserInteraction.setid("42");
        userUserInteraction.setusername("janedoe");
        Optional<UserUserInteraction> ofResult = Optional.of(userUserInteraction);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        assertTrue(this.userPostService.validate("42"));
        verify(this.userRepository).findById((String) any());
    }

    /**
     * Method under test: {@link UserPostService#validate(String)}
     */
    @Test
    void testValidate2() {
        when(this.userRepository.findById((String) any())).thenReturn(Optional.empty());
        assertFalse(this.userPostService.validate("42"));
        verify(this.userRepository).findById((String) any());
    }

    /**
     * Method under test: {@link UserPostService#validate(String)}
     */
    @Test
    void testValidate3() {
        when(this.userRepository.findById((String) any())).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.userPostService.validate("42"));
        verify(this.userRepository).findById((String) any());
    }

    /**
     * Method under test: {@link UserPostService#blockedBy(String)}
     */
    @Test
    void testBlockedBy() {
        UserPostInteraction userPostInteraction = new UserPostInteraction();
        ArrayList<String> stringList = new ArrayList<>();
        userPostInteraction.setBlockedBy(stringList);
        userPostInteraction.setFollowers(new ArrayList<>());
        userPostInteraction.setFollowing(new ArrayList<>());
        userPostInteraction.setUserId("42");
        userPostInteraction.setUserReports(new ArrayList<>());
        when(this.userPostRepository.findByUserId((String) any())).thenReturn(userPostInteraction);
        List<String> actualBlockedByResult = this.userPostService.blockedBy("42");
        assertSame(stringList, actualBlockedByResult);
        assertTrue(actualBlockedByResult.isEmpty());
        verify(this.userPostRepository).findByUserId((String) any());
    }

    /**
     * Method under test: {@link UserPostService#blockedBy(String)}
     */
    @Test
    void testBlockedBy2() {
        when(this.userPostRepository.findByUserId((String) any())).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.userPostService.blockedBy("42"));
        verify(this.userPostRepository).findByUserId((String) any());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link UserPostService}
     *   <li>{@link UserPostService#getAllFollowers()}
     * </ul>
     */
    @Test
    void testConstructor() {
        assertEquals("", (new UserPostService()).getAllFollowers());
    }
}

