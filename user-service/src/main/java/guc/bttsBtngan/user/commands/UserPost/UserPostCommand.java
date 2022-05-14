package guc.bttsBtngan.user.commands.UserPost;
import org.springframework.beans.factory.annotation.Autowired;

import guc.bttsBtngan.user.commands.Command;
import guc.bttsBtngan.user.services.UserPostService;
import guc.bttsBtngan.user.services.UserUserService;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class UserPostCommand extends Command {
    private UserPostService service;

    public UserPostService getService(){
        return service;
    }
  
    @Autowired
    public final void setService(UserPostService service){
        this.service=service;
    }
}

