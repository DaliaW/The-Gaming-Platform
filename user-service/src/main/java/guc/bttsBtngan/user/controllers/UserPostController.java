package guc.bttsBtngan.user.controllers;

import guc.bttsBtngan.user.services.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping(path = "user/user-post")
public class UserPostController {

    @Autowired
    private UserPostService userPostService;

    @GetMapping
    public String getAllFollowers() {
        return userPostService.getAllFollowers();
    }

    // moderator can see reports being made by users
    @GetMapping("users/reports")
    public String getAllReports(String moderatorId) {
        return userPostService.getAllReports(moderatorId);
    }

    @DeleteMapping(path = "users/block/{userId}")
    public String blockUser(@PathVariable("userId") String id) {
        // TODO: change myId to be taken from login session
        String myId = "1";
        return userPostService.blockUser(myId, id);
        //userUserService.deleteUser(id);
    }
    @DeleteMapping(path = "users/unblock/{userId}")
    public String unblockUser(@PathVariable("userId") String id) {
        // TODO: change myId to be taken from login session
        String myId = "1";
        return userPostService.unblockUser(myId, id);
        //userUserService.deleteUser(id);
    }

    @GetMapping(path = "users/recommendUser")
    public List<String> recommendUsers() {
        // TODO: change myId to be taken from login session
        String myId = "1";
        return userPostService.userRecommendations(myId);
        //userUserService.deleteUser(id);
    }

//    @PostMapping("users/repor")
//    public void testMD() {
//         userPostService.testMD();
//    }
    // moderator can ban users
//    @PostMapping("/ban")

}
