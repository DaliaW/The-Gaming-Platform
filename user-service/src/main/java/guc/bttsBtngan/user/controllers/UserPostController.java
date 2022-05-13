package guc.bttsBtngan.user.controllers;

import guc.bttsBtngan.user.services.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class UserPostController {

    @Autowired
    private UserPostService userPostService;

    @GetMapping
    public String getAllFollowers() {
        return userPostService.getAllFollowers();
    }

    // moderator can see reports being made by users
    @GetMapping("users/reports")
    public String getAllReports(@RequestBody HashMap<String, Object> body) throws Exception {
        return userPostService.getAllReports((String)body.get("user_id"));
    }

}
