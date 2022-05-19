package guc.bttsBtngan.authentication.commands.authentication;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class LoginCommand extends AuthenticationCommand{
    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        System.out.println("hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        String username =(String) map.get("username");
        String password =(String) map.get("password");

        return this.getService().login(username,password);
    }
}
