package guc.bttsBtngan.post.controllers;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import guc.bttsBtngan.post.data.Post;
import guc.bttsBtngan.post.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    public String searchPost(@RequestBody searchReques req) throws InterruptedException, ExecutionException {
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


    static public class tagRequest{
        String postId;
        String[]userIds;
    }
    static public class commentTagRequest{
        String postId;
        String commentId;
        String[]userIds;
    }
    static public class searchReques{
        String content;
    }

}
