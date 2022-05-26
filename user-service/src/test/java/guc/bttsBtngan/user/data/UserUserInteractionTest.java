package guc.bttsBtngan.user.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserUserInteractionTest {

    @Test
    void testConstructor() {
        UserUserInteraction actualUserUserInteraction = new UserUserInteraction();
        actualUserUserInteraction.setBanned(true);
        actualUserUserInteraction.setEmail("jane.doe@example.org");
        actualUserUserInteraction.setModerator(true);
        actualUserUserInteraction.setPassword("pass");
        actualUserUserInteraction.setid("42");
        actualUserUserInteraction.setusername("janedoe");
        assertEquals("jane.doe@example.org", actualUserUserInteraction.getEmail());
        assertEquals("pass", actualUserUserInteraction.getPassword());
        assertNull(actualUserUserInteraction.getPhotoRef());
        assertTrue(actualUserUserInteraction.isBanned());
        assertTrue(actualUserUserInteraction.isModerator());
    }


    @Test
    void testConstructor2() {
        UserUserInteraction actualUserUserInteraction = new UserUserInteraction("janedoe", "pass",
                "jane.doe@example.org", "Photo Ref", true, true);
        actualUserUserInteraction.setBanned(true);
        actualUserUserInteraction.setEmail("jane.doe@example.org");
        actualUserUserInteraction.setModerator(true);
        actualUserUserInteraction.setPassword("pass");
        actualUserUserInteraction.setid("42");
        actualUserUserInteraction.setusername("janedoe");
        assertEquals("jane.doe@example.org", actualUserUserInteraction.getEmail());
        assertEquals("pass", actualUserUserInteraction.getPassword());
        assertEquals("Photo Ref", actualUserUserInteraction.getPhotoRef());
        assertTrue(actualUserUserInteraction.isBanned());
        assertTrue(actualUserUserInteraction.isModerator());
    }

    @Test
    void testGetid() {
        assertNull((new UserUserInteraction()).getid());
    }

    @Test
    void testGetusername() {
        assertNull((new UserUserInteraction()).getusername());
    }
}

