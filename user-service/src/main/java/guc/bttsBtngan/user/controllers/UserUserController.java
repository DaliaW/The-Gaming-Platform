package guc.bttsBtngan.user.controllers;

import guc.bttsBtngan.user.data.UserUserInteraction;
import guc.bttsBtngan.user.services.UserUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long id) {
        userUserService.deleteUser(id);
    }

    @PutMapping(path = "{userId}")
    public void updateUser(@PathVariable("userId") Long id,
                           @RequestParam(required = false) String username,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String password) {
        userUserService.updateUser(id, username, email, password);
    }

    @GetMapping(path = "{photoRef}")
    public String getAllPhotos(@PathVariable("photoRef") String photoRef) {
        return userUserService.getAllphotoRef(photoRef);
    }

//    @GetMapping("/api/employeeswithvariable/{id}")
//    @ResponseBody
//    public String getEmployeesByIdWithVariableName(@PathVariable("id") String employeeId) {
//        return "ID: " + employeeId;
//    }

}
