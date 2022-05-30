package guc.bttsBtngan.user.data;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class UserPostInteractionTest {
    @Test
    void testConstructor() {
        UserPostInteraction actualUserPostInteraction = new UserPostInteraction();
        ArrayList<String> stringList = new ArrayList<>();
        actualUserPostInteraction.setBlockedBy(stringList);
        ArrayList<String> stringList1 = new ArrayList<>();
        actualUserPostInteraction.setFollowers(stringList1);
        ArrayList<String> stringList2 = new ArrayList<>();
        actualUserPostInteraction.setFollowing(stringList2);
        actualUserPostInteraction.setUserId("42");
        ArrayList<UserReports> userReportsList = new ArrayList<>();
        actualUserPostInteraction.setUserReports(userReportsList);
        assertSame(stringList, actualUserPostInteraction.getBlockedBy());
        assertSame(stringList1, actualUserPostInteraction.getFollowers());
        assertSame(stringList2, actualUserPostInteraction.getFollowing());
        assertEquals("42", actualUserPostInteraction.getUserId());
        assertSame(userReportsList, actualUserPostInteraction.getUserReports());
    }

    @Test
    void testConstructor2() {
        ArrayList<String> followers = new ArrayList<>();
        ArrayList<String> following = new ArrayList<>();
        ArrayList<String> blockedBy = new ArrayList<>();
        UserPostInteraction actualUserPostInteraction = new UserPostInteraction("42", followers, following, blockedBy,
                new ArrayList<>());
        ArrayList<String> stringList = new ArrayList<>();
        actualUserPostInteraction.setBlockedBy(stringList);
        ArrayList<String> stringList1 = new ArrayList<>();
        actualUserPostInteraction.setFollowers(stringList1);
        ArrayList<String> stringList2 = new ArrayList<>();
        actualUserPostInteraction.setFollowing(stringList2);
        actualUserPostInteraction.setUserId("42");
        ArrayList<UserReports> userReportsList = new ArrayList<>();
        actualUserPostInteraction.setUserReports(userReportsList);
        assertSame(stringList, actualUserPostInteraction.getBlockedBy());
        assertSame(stringList1, actualUserPostInteraction.getFollowers());
        assertSame(stringList2, actualUserPostInteraction.getFollowing());
        assertEquals("42", actualUserPostInteraction.getUserId());
        assertSame(userReportsList, actualUserPostInteraction.getUserReports());
    }
}

