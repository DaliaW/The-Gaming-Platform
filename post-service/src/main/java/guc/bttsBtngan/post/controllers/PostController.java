package guc.bttsBtngan.post.controllers;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import guc.bttsBtngan.post.data.Post;
import guc.bttsBtngan.post.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="/batates")
public class PostController {

    final private PostService service;
    @Autowired
    PostController(PostService service){
        this.service=service;
    }

    @PostMapping
    public String createPost(@RequestBody Post post) throws InterruptedException, ExecutionException {
        post.setModeratorId("3amo moderator");
        return service.createPost(post);
    }
    
    @PostMapping
    public String followPost(@RequestBody String userId,@RequestBody String postId) throws InterruptedException, ExecutionException {
        return service.followPost(userId,postId);
    }
    
    @PostMapping
    public String reportPost(@RequestBody String userId,@RequestBody String postId, @RequestBody String reportComment) throws InterruptedException, ExecutionException {
        return service.reportPost(userId,postId,reportComment);
    }
    
}
