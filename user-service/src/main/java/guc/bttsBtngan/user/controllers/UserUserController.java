package guc.bttsBtngan.user.controllers;

import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import guc.bttsBtngan.user.data.UserUserInteraction;
import guc.bttsBtngan.user.services.UserUserService;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import guc.bttsBtngan.user.commands.UserUser.UpdateUserCommand;
@RestController
//@RequestMapping(path = "users")
public class UserUserController {
    @Autowired
    private UpdateUserCommand updateUserCommand;

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


//    @DeleteMapping(path = "{userId}")
//    public void deleteUser(@PathVariable("userId") String id) {
//        userUserService.deleteUser(id);
//    }

    @PutMapping(path = "/users/user-profile")
    public void updateUser(@RequestParam(name="user_id") String id,
                           @RequestParam(name="username",required = false) String username,
                           @RequestParam(name="email",required = false) String email,
                           @RequestParam(name="oldPassword",required = false) String oldPassword,
                           @RequestParam(name="newPassword",required = false) String newPassword
                        ,@RequestParam(name="photo",required = false) MultipartFile photo
    ) throws Exception {
        System.out.println("IN UPDATE USER CONTROLLER");

        HashMap<String,Object>map=new HashMap<>();
        map.put("user_id",id);
        map.put("username",username);
        map.put("oldPassword",oldPassword);
        map.put("newPassword",newPassword);
        map.put("email",email);
        map.put("photo",photo);
        updateUserCommand.execute(map);
//        userUserService.updateUser(id, username, email, oldPassword, newPassword,photo);
    }
    @DeleteMapping(path = "/users/deleteProfilePicture")
    public void deleteProfilePicture(@RequestParam("user_id") String id) throws MinioException {
        System.out.println("in deleteeeeeee");
        userUserService.deleteProfilePicture(id);
    }



}
