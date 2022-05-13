package guc.bttsBtngan.post.controllers;


import java.util.concurrent.ExecutionException;

import guc.bttsBtngan.post.data.Post;
import guc.bttsBtngan.post.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
        return service.searchPosts(req.content);
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
    public String tagInPost(@RequestBody tagRequest req) throws InterruptedException, ExecutionException {
        return service.tagInPost(req.postId,req.userIds);
    }

    @DeleteMapping("/post/tag")
    public String delTagInPost(@RequestBody tagRequest req) throws InterruptedException, ExecutionException {
        return service.delTagInPost(req.postId,req.userIds);
    }

    @PutMapping("/post/comment/tag")
    public String tagInCommentPost(@RequestBody commentTagRequest req) throws InterruptedException, ExecutionException {
        return service.commentTagInPost(req.postId, req.commentId, req.userIds);
    }

    @DeleteMapping("/post/comment/tag")
    public String delTagInCommentPost(@RequestBody commentTagRequest req) throws InterruptedException, ExecutionException {
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

    static public class postRequest{
        public String postId;
        public  String userId;
    }
    static public class tagRequest{
        public String postId;
        String[]userIds;
    }
    static public class commentTagRequest{
        public String postId;
        public String commentId;
        public String[]userIds;
    }
    static public class searchRequest{
        public String content;
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
