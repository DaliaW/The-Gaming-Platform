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
    private UserUserService userUserService;

    @GetMapping("/users")
    public String getAllUsers() {
        // TODO: implement
        return userUserService.getAllUsers();
    }


    @PostMapping("/users/register")
    public void registerUser(@RequestBody UserUserInteraction user) {
        userUserService.registerUser(user);
    }


    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") String id) {
        userUserService.deleteUser(id);
    }

    @PutMapping(path = "/users/user-profile")
    public void updateUser(@RequestParam(name="user_id") String id,
                           @RequestParam(name="username",required = false) String username,
                           @RequestParam(name="email",required = false) String email,
                           @RequestParam(name="oldPassword",required = false) String oldPassword,
                           @RequestParam(name="newPassword",required = false) String newPassword
                        ,@RequestParam(name="photo",required = false) MultipartFile photo
    ) throws Exception {
        System.out.println("IN UPDATEEEEE USERRRRRRRR CONTROLLER");
        UpdateUserCommand x= new UpdateUserCommand();
        HashMap<String,Object>map=new HashMap<>();
        map.put("user_id",id);map.put("username",username);
        System.out.println("XXXXXXXXXXXXXXXXXXX= "+x==null);
        x.execute(map);
        boolean h= x.getService()==null;
        System.out.println("hhhhhhhhhhhhhhhhhhhhh= "+h);
        userUserService.updateUser(id, username, email, oldPassword, newPassword,photo);
    }

}
