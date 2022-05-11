package guc.bttsBtngan.user.controllers;

import guc.bttsBtngan.user.services.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


//    @PostMapping("users/repor")
//    public void testMD() {
//         userPostService.testMD();
//    }
    // moderator can ban users
//    @PostMapping("/ban")

}
