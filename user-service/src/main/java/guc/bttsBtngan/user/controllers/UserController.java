package guc.bttsBtngan.user.controllers;

import guc.bttsBtngan.user.services.UserUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserUserService userUserService;

    @GetMapping
    public String getAllUsers() {
        return userUserService.getAllUsers();
    }
}
