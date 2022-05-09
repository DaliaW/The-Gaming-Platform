package guc.bttsBtngan.user.commands.UserUser;

import guc.bttsBtngan.user.commands.Command;
import guc.bttsBtngan.user.services.UserUserService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class UserUserCommand extends Command {
    private UserUserService service;

    public UserUserService getService(){
        return service;

    }
    @Autowired
    public final void setService(UserUserService service){
        this.service=service;
    }

}
