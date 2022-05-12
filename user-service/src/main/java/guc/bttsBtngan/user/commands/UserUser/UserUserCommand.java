package guc.bttsBtngan.user.commands.UserUser;
import org.springframework.beans.factory.annotation.Autowired;
import guc.bttsBtngan.user.commands.Command;
import guc.bttsBtngan.user.services.UserUserService;


public abstract class UserUserCommand extends Command {
    private UserUserService service;

    public UserUserService getService(){
        boolean c=this.service==null;
        System.out.println("BBBBBBBBBBBBBBBB= "+c);
        return service;

    }
    @Autowired
    public final void setService(UserUserService service){
        boolean a=service==null;
        System.out.println("AAAAAAAAAAAAAA= "+a);
        this.service=service;
        boolean c=service==null;
        System.out.println("DDDDDDDDDDD= "+c);
    }

}
