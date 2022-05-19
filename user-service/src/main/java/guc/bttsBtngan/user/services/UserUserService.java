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


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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
        int strength=10;

        this.passwordEncoder=new BCryptPasswordEncoder(strength,new SecureRandom());
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
        System.out.println(photo==null);
        // update a user
        UserUserInteraction user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User does not exist"));

        // if the name not equal to null, not empty & not the same as the current name
        if(username != null && username.length() > 0 && !Objects.equals(username, user.getusername())) {
            // update the name
            user.setusername(username);
        }
        // if email is not null, not empty & not the same as the current email & not already have been taken
        if(email != null && email.length() > 0 && !Objects.equals(email, user.getEmail())){
            Optional<UserUserInteraction> emailExists = userRepository.findByEmail(email);
            // check if the email is already registered
            if (!emailExists.isPresent()) {
                user.setEmail(email);

            } else {
                throw new IllegalStateException("Email already exists");

            }

        }
        // if password is not null, not empty & not the same as the current password
            if((oldPassword ==null ||oldPassword.length()==0)&& newPassword!=null) {
                throw new IllegalStateException("Please enter old password.");
            }
        if(oldPassword != null && oldPassword.length()>0){

//            if(!Objects.equals(encryptedPassword, user.getPassword())) {
            if(!passwordEncoder.matches(oldPassword,user.getPassword())){
                throw new IllegalStateException("Old Password is incorrect.");
            }
            else if(newPassword!=null && newPassword.length()>0){
                if(!passwordEncoder.matches(newPassword,user.getPassword())){
                    String encryptedNewPassword= passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedNewPassword);
                }
                else
                    throw new IllegalStateException("New password is same as old password.");

            }
            else
                throw new IllegalStateException("Please enter new password");
        }
        if(photo!=null ){
            System.out.println("phottt here");
            String textPath=minioConfigurationProperties.getBucket();
            System.out.println("bucket="+textPath);
//            textPath+="/";
            String uniqueID = UUID.randomUUID().toString();
            textPath+=uniqueID;
//            String imgName= photo.getOriginalFilename();
//            textPath+=imgName;
//             textPath+="monica.png";
            Path source = Paths.get(textPath);
            System.out.println("path= "+source);
            InputStream file=photo.getInputStream();
            String contentType=photo.getContentType();
            minioService.upload(source,file,contentType);
            user.setPhotoRef(textPath);
        }


        return "Profile updated successfully";

    }
    @Transactional
    public String deleteProfilePicture(String id) throws MinioException {
        System.out.println("in delete");
        UserUserInteraction user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User does not exist"));
        String photoRef= user.getPhotoRef();
        System.out.println("photoRef= "+photoRef);
        if(photoRef!=null && photoRef.length()>0) {
            Path source = Paths.get(photoRef);
            minioService.remove(source);
            user.setPhotoRef("");
            System.out.println("after= "+user.getPhotoRef());
        }
        else{
            throw new IllegalStateException("No photo to delete.");
        }
        System.out.println("after= "+user.getPhotoRef());
        return "Profile picture deleted successfully";


    }

//    public void test() throws MinioException, IOException {
//        InputStream s= minioService.get(Paths.get("scalableimages/req.png"));
//        Image image = ImageIO.read(s);
//        JFrame frame = new JFrame();
//        JLabel label = new JLabel(new ImageIcon(image));
//        frame.getContentPane().add(label, BorderLayout.CENTER);
//        frame.pack();
//        frame.setVisible(true);
//        System.out.println(s);
//    }
}
