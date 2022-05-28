package guc.bttsBtngan.user.controllers;

import guc.bttsBtngan.user.data.UserUserInteraction;
import guc.bttsBtngan.user.services.UserUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
@RestController
public class UserUserController {

    @Autowired
    private UserUserService userUserService;

    // ApiOperation for swagger ui configuration
    @ApiOperation(value = "Get all users in the website", response = UserUserInteraction.class, responseContainer = "List")
    @GetMapping("/users")
    public String getAllUsers() {
        // TODO: implement
        return userUserService.getAllUsers();
    }

    // ApiOperation for swagger ui configuration
    @ApiOperation(value = "Register a new user")
    @ApiParam(name = "body", value = "The user to be registered", required = true, example = "{\n" +
            "  \"username\": \"username\",\n" +
            "  \"password\": \"password\",\n" +
            "  \"email\": \"email\",\n" +
            "  \"firstName\": \"firstName\",\n" +
            "  \"lastName\": \"lastName\"\n" +
            "}")
    @PostMapping("/users/register")
    public void registerUser(@RequestBody UserUserInteraction user) {
        userUserService.registerUser(user);
    }

    // ApiOperation for swagger ui configuration
    @ApiOperation(value = "delete a user")
    @ApiParam(name = "user_id", value = "The user to be deleted", required = true, example = "c78181a980c794230180c794b3260000")
    @DeleteMapping(path = "/users/user-profile/delete")
    public void deleteUser(@RequestBody HashMap<String,Object> body) throws Exception {
        userUserService.DeleteUser((String) body.get("user_id"));
    }

    // ApiOperation for swagger ui configuration
    @ApiOperation(value = "update the user's profile")
    @PutMapping(path = "/users/user-profile")
    public void updateUser(@RequestParam(name="user_id") String id,
                           @RequestParam(name="username",required = false) String username,
                           @RequestParam(name="email",required = false) String email,
                           @RequestParam(name="oldPassword",required = false) String oldPassword,
                           @RequestParam(name="newPassword",required = false) String newPassword
                        ,@RequestParam(name="photo",required = false) MultipartFile photo
    ) throws Exception {
        userUserService.updateUser(id, username, email, oldPassword, newPassword, photo);
    }

    // ApiOperation for swagger ui configuration
    @ApiOperation(value = "Delete a user's profile picture")
    @DeleteMapping(path = "/users/deleteProfilePicture")
    public void deleteProfilePicture(@RequestParam("user_id") String id) throws Exception {
        userUserService.deleteProfilePicture(id);
    }

    // ApiOperation for swagger ui configuration
    @ApiOperation(value = "Get all user's profile pictures")
    @GetMapping(path = "users/photo/{photoRef}")
    public String getAllphotoRef(@PathVariable("photoRef") String photoRef) {
        return userUserService.getAllphotoRef(photoRef);
    }

    // ApiOperation for swagger ui configuration
    @ApiOperation(value = "Moderator can ban a user from the website")
    // moderator can ban users
    @PostMapping(path = "users/ban")
    public String banUser(@RequestBody HashMap<String, Object> body) {
        return userUserService.banUser((String)body.get("user_id") ,(String)body.get("userToBan"));
    }

    // ApiOperation for swagger ui configuration
    @ApiOperation(value = "Moderator can unban a user from the website")
    // moderator can unban users
    @PostMapping(path = "users/unban")
    public String unbanUser(@RequestBody HashMap<String, Object> body) {
        return userUserService.unbanUser((String)body.get("user_id") ,(String)body.get("userToUnban"));
    }

    // ApiOperation for swagger ui configuration
    @ApiOperation(value = "follow a user")
    @PutMapping(path = "/users/follow/{userId}/{userToBeFollowed}")
    public String followUser(@PathVariable("userId") String userId,
                             @PathVariable("userToBeFollowed") String userToBeFollowedId
    ) throws Exception {
        HashMap<String, Object> map=new HashMap<>();
        map.put("user_id",userId);
        map.put("userToFollowId",userToBeFollowedId);
        return userUserService.followUser(userId, userToBeFollowedId);
    }

    // ApiOperation for swagger ui configuration
    @ApiOperation(value = "unfollow a user")
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
