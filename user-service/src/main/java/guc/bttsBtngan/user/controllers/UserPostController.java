package guc.bttsBtngan.user.controllers;

import guc.bttsBtngan.user.commands.UserPost.ReportUserCommand;
import guc.bttsBtngan.user.services.UserPostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class UserPostController {

//    @Autowired
//    private BlockUserCommand blockUserCommand;
//
//    @Autowired
//    private UnblockUserCommand unblockUserCommand;
//
//    @Autowired
//    private RecommendUserCommand recommendUserCommand;
    @Autowired
    private UserPostService userPostService;

    @Autowired
    private ReportUserCommand reportUserCommand;

    // ApiOperation for swagger ui configuration
    @ApiOperation(value = "get all followers")
    @GetMapping
    public String getAllFollowers() {
        return userPostService.getAllFollowers();
    }

    // ApiOperation for swagger ui configuration
    @ApiOperation(value = "Moderator can see reports being made by users")
    // moderator can see reports being made by users
    @GetMapping("users/reports")
    public String getAllReports(@RequestBody HashMap<String, Object> body) throws Exception {
        return userPostService.getAllReports((String)body.get("user_id"));
    }

    // ApiOperation for swagger ui configuration
    @ApiOperation(value = "user report other user")
    @PostMapping(path = "users/report")
//    public String reportUser(@RequestBody HashMap<String, Object> map) throws Exception {
        public String reportUser(@RequestParam String user_id, @RequestParam String userId2, @RequestParam String reportComment ) throws Exception {
//        String myId = "1";
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",user_id);
        map.put("userId2",userId2);
        map.put("reportComment",reportComment);
//        System.out.println("report user:-  "+ (String) reportUserCommand.execute(map));
//        return (String) reportUserCommand.execute(map);
//        System.out.print("ana henaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//        return userPostService.reportUser((String) map.get("userId"),(String) map.get("userId2"),(String) map.get("reportComment"));
//        return userPostService.reportUser("userId","userId2","reportComment");
        return userPostService.reportUser(user_id,userId2,reportComment);

    }

    // ApiOperation for swagger ui configuration
    @ApiOperation(value = "user can block other user")
    @DeleteMapping(path = "users/block")
    public String blockUser(@RequestParam("user_id") String myId, @RequestParam("otherId") String otherId) throws Exception {
//        String myId = "1";
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id",myId);
        map.put("otherId",otherId);
        //return (String)blockUserCommand.execute(map);
        return userPostService.blockUser(myId, otherId);
        //userUserService.deleteUser(id);
    }

    // ApiOperation for swagger ui configuration
    @ApiOperation(value = "user can unblock other user")
    @DeleteMapping(path = "users/unblock/")
    public String unblockUser(@RequestParam("user_id") String myId, @RequestParam("otherId") String otherId) throws Exception {
//        String myId = "1";
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id",myId);
        map.put("otherId",otherId);
        System.out.println("in");
        //return (String)unblockUserCommand.execute(map);
        return userPostService.unblockUser(myId, otherId);
        //userUserService.deleteUser(id);
    }

    // ApiOperation for swagger ui configuration
    @ApiOperation(value = "show user recommendations to user based on his/her followers")
    @GetMapping(path = "users/recommendUser")
    public List<String> recommendUsers(@RequestParam("user_id") String myId) throws Exception {
//        String myId = "1";
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id",myId);
        // return (List<String>)recommendUserCommand.execute(map);
        return userPostService.userRecommendations(myId);
        //userUserService.deleteUser(id);
    }
}
