package guc.bttsBtngan.post.commands;

import guc.bttsBtngan.post.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class PostCommand extends Command{

    @Autowired
    private PostService service;

    public PostService getService() {
        return service;
    }

    @Autowired
    public final void setService(PostService service) {
        this.service = service;
    }
}