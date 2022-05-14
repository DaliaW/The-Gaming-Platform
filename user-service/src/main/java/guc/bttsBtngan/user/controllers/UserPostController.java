package guc.bttsBtngan.user.controllers;

import guc.bttsBtngan.user.commands.UserPost.BlockUserCommand;
import guc.bttsBtngan.user.commands.UserPost.RecommendUserCommand;
import guc.bttsBtngan.user.commands.UserPost.UnblockUserCommand;
import guc.bttsBtngan.user.services.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import java.util.HashMap;

@RestController
public class UserPostController {

    //instance from userpostcommands
    @Autowired
    private BlockUserCommand blockUserCommand;

    @Autowired
    private UnblockUserCommand unblockUserCommand;

    @Autowired
    private RecommendUserCommand recommendUserCommand;

    @Autowired
    private UserPostService userPostService;

    public BlockUserCommand getBlockUserCommand() {
        return blockUserCommand;
    }

    public void setBlockUserCommand(BlockUserCommand blockUserCommand) {
        this.blockUserCommand = blockUserCommand;
    }

    public void setUnblockUserCommand(UnblockUserCommand unblockUserCommand) {
        this.unblockUserCommand = unblockUserCommand;
    }

    public void setRecommendUserCommand(RecommendUserCommand recommendUserCommand) {
        this.recommendUserCommand = recommendUserCommand;
    }

    public UnblockUserCommand getUnblockUserCommand() {
        return unblockUserCommand;
    }

    public RecommendUserCommand getRecommendUserCommand() {
        return recommendUserCommand;
    }



    @GetMapping
    public String getAllFollowers() {
        return userPostService.getAllFollowers();
    }

    // moderator can see reports being made by users
    @GetMapping("users/reports")
    public String getAllReports(@RequestBody HashMap<String, Object> body) throws Exception {
        return userPostService.getAllReports((String)body.get("user_id"));
    }

    @DeleteMapping(path = "users/block")
    public String blockUser(@RequestParam("user_id") String myId, @RequestParam("otherId") String otherId) throws Exception {
//        String myId = "1";
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id",myId);
        map.put("otherId",otherId);
        return (String)blockUserCommand.execute(map);
//        return userPostService.blockUser(myId, otherId);
        //userUserService.deleteUser(id);
    }
    @DeleteMapping(path = "users/unblock/")
    public String unblockUser(@RequestParam("user_id") String myId, @RequestParam("otherId") String otherId) throws Exception {
//        String myId = "1";
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id",myId);
        map.put("otherId",otherId);
        System.out.println("in");
        return (String)unblockUserCommand.execute(map);
//        return userPostService.unblockUser(myId, otherId);
        //userUserService.deleteUser(id);
    }

    @GetMapping(path = "users/recommendUser")
    public List<String> recommendUsers(@RequestParam("user_id") String myId) throws Exception {
//        String myId = "1";
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id",myId);
        return (List<String>)recommendUserCommand.execute(map);
//        return userPostService.userRecommendations(myId);
        //userUserService.deleteUser(id);
    }

}
