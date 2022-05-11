package guc.bttsBtngan.authentication.commands.authentication;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class VerifyCommand extends AuthenticationCommand{
    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        String token =(String) map.get("token");

        return this.getService().verify(token);
    }
}
