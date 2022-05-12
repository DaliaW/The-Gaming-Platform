package guc.bttsBtngan.user.services;

import com.jlefebure.spring.boot.minio.MinioConfiguration;
import com.jlefebure.spring.boot.minio.MinioConfigurationProperties;
import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import guc.bttsBtngan.user.data.UserPostInteraction;
import guc.bttsBtngan.user.data.UserUserInteraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service // to specify that this class is responsible for the business logic
public class UserUserService {
    @Autowired
    private MinioService minioService;
    @Autowired
    private MinioConfigurationProperties minioConfigurationProperties;
    // this class will deal with all user-user interaction and database operations in postgres

    private final UserRepository userRepository;
    // the repository for the user table in postgres to deal with database operations including CRUD operations

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder=new BCryptPasswordEncoder();
    }

    @GetMapping
    public String getAllUsers() {
        return userRepository.findAll().toString();
    }



    public void registerUser(UserUserInteraction user) {
        // register a user
        Optional<UserUserInteraction> email = userRepository.findByEmail(user.getEmail());

        // check if the email is already registered
        if (email.isPresent()) {
            // if the user email already exists
            throw new IllegalStateException("Email already exists");
        }
        // TODO: password hashing
        // TODO: email verification
        // if the email is not registered yet then save the user
        String encryptedPassword= passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    public void deleteUser(String id) {
        // delete a user
        boolean exists = userRepository.existsById(id);
        // check if the user exists
        if (!exists) {
            // if the user does not exist
            throw new IllegalStateException("User does not exist");
        }
        // if the user exists then delete the user
        userRepository.deleteById(id);
    }

//    @Transactional
//    public String updateUser(){
//        System.out.println("IN UPDATE SERVICE");
//        return "IN UPDATE";
//    }


        @Transactional
    public String updateUser(String id, String username, String email, String oldPassword, String newPassword, MultipartFile photo) throws IOException, MinioException {

        System.out.println("id= "+id);
            System.out.println("username= "+username);
        // update a user
        System.out.println("IN UPDATE SERVICEE");
        UserUserInteraction user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User does not exist"));
        System.out.println("IN AFTERR UPDATEE");

        // if the name not equal to null, not empty & not the same as the current name
        if(username != null && username.length() > 0 && !Objects.equals(username, user.getusername())) {
            // update the name
            user.setusername(username);
            System.out.println("new username saved= "+username);
        }
        // if email is not null, not empty & not the same as the current email & not already have been taken
        if(email != null && email.length() > 0 && !Objects.equals(email, user.getEmail())){
            Optional<UserUserInteraction> emailExists = userRepository.findByEmail(email);
            // check if the email is already registered
            if (emailExists.isPresent()) {
                // if the user email already exists
                throw new IllegalStateException("Email already exists");
            } else {
                // update the email
                user.setEmail(email);
            }
        }
        // if password is not null, not empty & not the same as the current password
        if(oldPassword != null && oldPassword.length()>0){
            if( !Objects.equals(oldPassword, user.getPassword())) {
                throw new IllegalStateException("Old Password is incorrect.");
            }
            else if(newPassword!=null && newPassword.length()>0){
                if(!Objects.equals(newPassword, user.getPassword())){
                    user.setPassword(newPassword);
                }
                else
                    throw new IllegalStateException("New password is same as old password.");

            }
            else
                throw new IllegalStateException("Please enter new password");
        }
        if(photo!=null ){
            String textPath=minioConfigurationProperties.getBucket();
            textPath+="/";
            String imgName= photo.getOriginalFilename();
            textPath+=imgName;
            Path source = Paths.get(textPath);
            InputStream file=photo.getInputStream();
            String contentType=photo.getContentType();
            minioService.upload(source,file,contentType);
        }


        return "User updated successfully";

    }
}
