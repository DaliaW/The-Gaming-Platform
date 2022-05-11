package guc.bttsBtngan.post.controllers;

import com.jlefebure.spring.boot.minio.MinioException;
import guc.bttsBtngan.post.data.UserUserInteraction;
import guc.bttsBtngan.post.services.UserUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "user/user-profile")
public class UserUserController {

    @Autowired
    private UserUserService userUserService;
    @GetMapping
    public String getAllUsers() {
        // TODO: implement
        return userUserService.getAllUsers();
    }




    @PostMapping
    public void registerUser(@RequestBody UserUserInteraction user) {
        userUserService.registerUser(user);
    }

//    @PostMapping
//    public void login(@RequestParam String email,
//                      @RequestParam String password) {
//        userUserService.login(email,password);
//    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") long id) {
        userUserService.deleteUser(id);
    }

    @PutMapping(path = "{userId}")
    public void updateUser(@PathVariable("userId") long id,
                           @RequestParam(required = false) String username,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String oldPassword,
                           @RequestParam(required = false) String newPassword
                        ,@RequestParam(name="photo",required = false) MultipartFile photo
    ) throws MinioException, IOException {
        userUserService.updateUser(id, username, email, oldPassword, newPassword,photo);
    }

    @GetMapping(path = "{photoRef}")
    public String getAllPhotos(@PathVariable("photoRef") String photoRef) {
        return userUserService.getAllphotoRef(photoRef);
    }



}
