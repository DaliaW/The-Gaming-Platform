package guc.bttsBtngan.user.controllers;

//import com.jlefebure.spring.boot.minio.MinioException;

import guc.bttsBtngan.user.data.UserUserInteraction;
import guc.bttsBtngan.user.services.UserUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
@RestController
//@RequestMapping(path = "users")
public class UserUserController {

//    @Autowired
//    private FollowUserCommand followUserCommand;
//    @Autowired
//    private UnfollowUserCommand unfollowUserCommand;

//    @Autowired
//    private UpdateUserCommand updateUserCommand;
//
//    @Autowired
//    private DeleteProfilePicCommand DeleteProfilePicCommand;


    @Autowired
    private UserUserService userUserService;

    @GetMapping("/users")
    public String getAllUsers() {
        // TODO: implement
        return userUserService.getAllUsers();
    }
//    @GetMapping("/test")
//    public void test() throws MinioException, IOException {
//        System.out.println("TESTTTTTTTTTTT");
//        // TODO: implement
//         userUserService.test();
//    }



    @PostMapping("/users/register")
    public void registerUser(@RequestBody UserUserInteraction user) {
        userUserService.registerUser(user);
    }

    @DeleteMapping(path = "/users/user-profile/delete")
    public void deleteUser(@RequestBody HashMap<String,Object> body) throws Exception {
//        public void deleteUser(@RequestBody String userId) throws Exception {
//        HashMap<String, Object> map=new HashMap<>();
//        map.put("userId",userId);
//        return (String) deleteUserCommand.execute(map);

        userUserService.DeleteUser((String) body.get("user_id"));
//        return "the user has been deleted";

    }

    @PutMapping(path = "/users/user-profile")
    public void updateUser(@RequestParam(name="user_id") String id,
                           @RequestParam(name="username",required = false) String username,
                           @RequestParam(name="email",required = false) String email,
                           @RequestParam(name="oldPassword",required = false) String oldPassword,
                           @RequestParam(name="newPassword",required = false) String newPassword
                        ,@RequestParam(name="photo",required = false) MultipartFile photo
    ) throws Exception {
        userUserService.updateUser(id, username, email, oldPassword, newPassword, photo);
        // HashMap<String,Object>map=new HashMap<>();
        // map.put("user_id",id);
        // map.put("username",username);
        // map.put("oldPassword",oldPassword);
        // map.put("newPassword",newPassword);
        // map.put("email",email);
        // map.put("photo",photo);
        // updateUserCommand.execute(map);
    
    }
    @DeleteMapping(path = "/users/deleteProfilePicture")
    public void deleteProfilePicture(@RequestParam("user_id") String id) throws Exception {
//         HashMap<String,Object>map=new HashMap<>();
//         map.put("user_id",id);
//        DeleteProfilePicCommand.execute(map);
        userUserService.deleteProfilePicture(id);
    }

    @GetMapping(path = "users/photo/{photoRef}")
    public String getAllphotoRef(@PathVariable("photoRef") String photoRef) {
        return userUserService.getAllphotoRef(photoRef);
    }


    // moderator can ban users
    @PostMapping(path = "users/ban")
    public String banUser(@RequestBody HashMap<String, Object> body) {
        return userUserService.banUser((String)body.get("user_id") ,(String)body.get("userToBan"));
    }


    // moderator can unban users
    @PostMapping(path = "users/unban")
    public String unbanUser(@RequestBody HashMap<String, Object> body) {
        return userUserService.unbanUser((String)body.get("user_id") ,(String)body.get("userToUnban"));
    }

    @DeleteMapping(path = "block/{userId}")
    public void blockuser(@PathVariable("userId") Long id) {
        // TODO: implement
        //userUserService.deleteUser(id);
    }
    @DeleteMapping(path = "unblock/{userId}")
    public void unblockuser(@PathVariable("userId") Long id) {
        // TODO: implement
        //userUserService.deleteUser(id);
    }

//    @GetMapping("/api/employeeswithvariable/{id}")
//    @ResponseBody
//    public String getEmployeesByIdWithVariableName(@PathVariable("id") String employeeId) {
//        return "ID: " + employeeId;
//    }

    @PutMapping(path = "/users/follow")
    public String followUser(@RequestBody HashMap<String, Object> body) throws Exception {
        return userUserService.followUser((String)body.get("userId"), (String)body.get("userToFollow"));
    }

    @PutMapping(path = "/users/unfollow/{userId}/{userToBeUnfollowed}")
    public String unfollowUser(@PathVariable("userId") String userId,
                               @PathVariable("userToBeUnfollowed") String userToBeUnfollowedId
    ) throws Exception {
        HashMap<String, Object> map=new HashMap<>();
        map.put("user_id",userId);
        map.put("userToUnfollowId",userToBeUnfollowedId);
        //return (String) unfollowUserCommand.execute(map);
        return userUserService.unfollowUser(userId, userToBeUnfollowedId);
    }

}
