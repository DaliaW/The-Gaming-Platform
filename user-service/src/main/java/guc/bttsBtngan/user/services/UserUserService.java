package guc.bttsBtngan.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class UserUserService {
    // this class will deal with all user-user interaction and database operations in postgres

    private final UserRepository userRepository;
    // the repository for the user table in postgres to deal with database operations including CRUD operations

    @Autowired
    public UserUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getAllUsers() {
        return userRepository.findAll().toString();
    }
}
