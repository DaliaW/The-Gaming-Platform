package guc.bttsBtngan.post.controllers;


import java.util.concurrent.ExecutionException;

import guc.bttsBtngan.post.data.Post;
import guc.bttsBtngan.post.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class PostController {

    final private PostService service;
    @Autowired
    PostController(PostService service){
        this.service=service;
    }

    @PostMapping("/post")
    public String createPost(@RequestBody Post post) throws InterruptedException, ExecutionException {
        return service.createPost(post);
    }

    @PostMapping("/post/content")
    public String searchPost(@RequestBody searchRequest req) throws InterruptedException, ExecutionException {
        return service.searchPosts(req.content,req.userId);
    }

    @PostMapping("/post/follow")
    public String followPost(@RequestBody followPostRequest req) throws InterruptedException, ExecutionException {
        return service.followPost(req.userId,req.postId);
    }

    @PostMapping("/post/report")
    public String reportPost(@RequestBody reportPostRequest req) throws InterruptedException, ExecutionException {
        return service.reportPost(req.userId,req.postId,req.reportComment);
    }

    @PutMapping("/post/tag")
    public String tagInPost(@RequestBody tagRequest req) throws Exception {
        return service.tagInPost(req.postId,req.userIds,req.userIdSending);
    }

    @DeleteMapping("/post/tag")
    public String delTagInPost(@RequestBody tagRequest req) throws Exception {
        return service.delTagInPost(req.postId,req.userIds);
    }

    @PutMapping("/post/comment/tag")
    public String tagInCommentPost(@RequestBody commentTagRequest req) throws Exception {
        return service.commentTagInPost(req.postId, req.commentId, req.userIds,req.userIdSending);
    }

    @DeleteMapping("/post/comment/tag")
    public String delTagInCommentPost(@RequestBody commentTagRequest req) throws Exception {
        return service.delCommentTagInPost(req.postId,req.commentId,req.userIds);
    }
    
    @PostMapping("/post/assignmod")
    public String assignModerator(@RequestBody postRequest req) throws InterruptedException, ExecutionException {
        return service.assignModerator(req.postId, req.userId);
    }
    
    @PostMapping("/post/modcheckrep")
    public String checkPostReports(@RequestBody postRequest req) throws InterruptedException, ExecutionException, ResponseStatusException {
        return service.checkPostReports(req.postId, req.userId);
    }


    @PutMapping(path = "/posts/attach-photo")
    public void updatePost(@RequestParam(name="post_id") String id,
                           @RequestParam(name="photo",required = false) MultipartFile photo
    ) throws Exception {
        System.out.println("IN UPDATE USER CONTROLLER");
//        userUserService.updateUser(id, username, email, oldPassword, newPassword, photo);
        service.attachImageToPost(id,photo);
        // HashMap<String,Object>map=new HashMap<>();
        // map.put("user_id",id);
        // map.put("username",username);
        // map.put("oldPassword",oldPassword);
        // map.put("newPassword",newPassword);
        // map.put("email",email);
        // map.put("photo",photo);
        // updateUserCommand.execute(map);

    }
    static public class postRequest{
        public String postId;
        public  String userId;
    }
    static public class tagRequest{
        public String postId,userIdSending;
        String[]userIds;
    }
    static public class commentTagRequest{
        public String postId,userIdSending;
        public String commentId;
        public String[]userIds;
    }
    static public class searchRequest{
        public String content;
        public String userId;
    }
    static public class followPostRequest{
        public String userId;
        public String postId;
    }
    
    static public class reportPostRequest{
        public String userId;
        public String postId;
        public String reportComment;
    }
}
