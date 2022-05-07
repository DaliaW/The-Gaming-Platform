package guc.bttsBtngan.post.controllers;

import java.util.HashMap;
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
        post.setModeratorId("3amo moderator");
        return service.createPost(post);
    }

    @PostMapping("/post/content")
    public String searchPost(@RequestBody searchRequest req) throws InterruptedException, ExecutionException {
        return service.searchPosts(req.content);
    }

    @PostMapping("/post/follow")
    public String followPost(@RequestBody String userId,@RequestBody String postId) throws InterruptedException, ExecutionException {
        return service.followPost(userId,postId);
    }

    @PostMapping("/post/report")
    public String reportPost(@RequestBody String userId,@RequestBody String postId, @RequestBody String reportComment) throws InterruptedException, ExecutionException {
        return service.reportPost(userId,postId,reportComment);
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
    public String assignModerator(@RequestBody String postId,@RequestBody String userId) throws InterruptedException, ExecutionException {
        return service.assignModerator(postId,userId);
    }
    
    @PostMapping("/post/modcheckrep")
    public String checkPostReports(@RequestBody String postId,@RequestBody String userId) throws InterruptedException, ExecutionException, ResponseStatusException {
        return service.checkPostReports(userId,postId);
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

}
