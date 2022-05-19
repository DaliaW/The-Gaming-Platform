package guc.bttsBtngan.authentication.commands.authentication;

import guc.bttsBtngan.authentication.commands.Command;
import guc.bttsBtngan.authentication.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AuthenticationCommand extends Command {
    private AuthService authService;

    public AuthService getService() {
        return authService;
    }
    @Autowired
    public final void setService(AuthService service){
        this.authService = service;
    }
}
