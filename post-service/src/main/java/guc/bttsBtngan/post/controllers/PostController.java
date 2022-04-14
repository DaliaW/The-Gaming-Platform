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
    public String createPost() throws InterruptedException, ExecutionException {
        return service.createPost();
    }
}
