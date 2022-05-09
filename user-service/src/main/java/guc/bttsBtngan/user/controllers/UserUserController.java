package guc.bttsBtngan.user.controllers;

import guc.bttsBtngan.user.data.UserUserInteraction;
import guc.bttsBtngan.user.services.UserUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable Long id) {
        return userUserService.getUser(id);
    }

    @PostMapping("/users/register")
    public void registerUser(@RequestBody UserUserInteraction user) {
        userUserService.registerUser(user);
    }

    @DeleteMapping(path = "/users/user-profile/{userId}")
    public void deleteUser(@PathVariable("userId") Long id) {
        userUserService.deleteUser(id);
    }

    @PutMapping(path = "/users/user-profile/{userId}")
    public void updateUser(@PathVariable("userId") Long id,
                           @RequestParam(required = false) String username,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String password) {
        userUserService.updateUser(id, username, email, password);
    }

    @GetMapping(path = "users/photo/{photoRef}")
    public String getAllphotoRef(@PathVariable("photoRef") String photoRef) {
        return userUserService.getAllphotoRef(photoRef);
    }

}
