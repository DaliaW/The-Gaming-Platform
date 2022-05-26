package guc.bttsBtngan.user.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UserUserInteractionTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link UserUserInteraction#UserUserInteraction()}
     *   <li>{@link UserUserInteraction#setBanned(boolean)}
     *   <li>{@link UserUserInteraction#setEmail(String)}
     *   <li>{@link UserUserInteraction#setModerator(boolean)}
     *   <li>{@link UserUserInteraction#setPassword(String)}
     *   <li>{@link UserUserInteraction#setid(String)}
     *   <li>{@link UserUserInteraction#setusername(String)}
     *   <li>{@link UserUserInteraction#getEmail()}
     *   <li>{@link UserUserInteraction#getPassword()}
     *   <li>{@link UserUserInteraction#getPhotoRef()}
     *   <li>{@link UserUserInteraction#isBanned()}
     *   <li>{@link UserUserInteraction#isModerator()}
     * </ul>
     */
    @Test
    void testConstructor() {
        UserUserInteraction actualUserUserInteraction = new UserUserInteraction();
        actualUserUserInteraction.setBanned(true);
        actualUserUserInteraction.setEmail("jane.doe@example.org");
        actualUserUserInteraction.setModerator(true);
        actualUserUserInteraction.setPassword("iloveyou");
        actualUserUserInteraction.setid("42");
        actualUserUserInteraction.setusername("janedoe");
        assertEquals("jane.doe@example.org", actualUserUserInteraction.getEmail());
        assertEquals("iloveyou", actualUserUserInteraction.getPassword());
        assertNull(actualUserUserInteraction.getPhotoRef());
        assertTrue(actualUserUserInteraction.isBanned());
        assertTrue(actualUserUserInteraction.isModerator());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link UserUserInteraction#UserUserInteraction(String, String, String, String, boolean, boolean)}
     *   <li>{@link UserUserInteraction#setBanned(boolean)}
     *   <li>{@link UserUserInteraction#setEmail(String)}
     *   <li>{@link UserUserInteraction#setModerator(boolean)}
     *   <li>{@link UserUserInteraction#setPassword(String)}
     *   <li>{@link UserUserInteraction#setid(String)}
     *   <li>{@link UserUserInteraction#setusername(String)}
     *   <li>{@link UserUserInteraction#getEmail()}
     *   <li>{@link UserUserInteraction#getPassword()}
     *   <li>{@link UserUserInteraction#getPhotoRef()}
     *   <li>{@link UserUserInteraction#isBanned()}
     *   <li>{@link UserUserInteraction#isModerator()}
     * </ul>
     */
    @Test
    void testConstructor2() {
        UserUserInteraction actualUserUserInteraction = new UserUserInteraction("janedoe", "iloveyou",
                "jane.doe@example.org", "Photo Ref", true, true);
        actualUserUserInteraction.setBanned(true);
        actualUserUserInteraction.setEmail("jane.doe@example.org");
        actualUserUserInteraction.setModerator(true);
        actualUserUserInteraction.setPassword("iloveyou");
        actualUserUserInteraction.setid("42");
        actualUserUserInteraction.setusername("janedoe");
        assertEquals("jane.doe@example.org", actualUserUserInteraction.getEmail());
        assertEquals("iloveyou", actualUserUserInteraction.getPassword());
        assertEquals("Photo Ref", actualUserUserInteraction.getPhotoRef());
        assertTrue(actualUserUserInteraction.isBanned());
        assertTrue(actualUserUserInteraction.isModerator());
    }

    /**
     * Method under test: {@link UserUserInteraction#getid()}
     */
    @Test
    void testGetid() {
        assertNull((new UserUserInteraction()).getid());
    }

    /**
     * Method under test: {@link UserUserInteraction#getusername()}
     */
    @Test
    void testGetusername() {
        assertNull((new UserUserInteraction()).getusername());
    }
}

