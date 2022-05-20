package guc.bttsBtngan.user.commands.UserUser;

import guc.bttsBtngan.user.data.UserUserInteraction;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class RegisterUserCommand extends UserUserCommand {

    @Override
    public Object execute(HashMap<String, Object> map) {
        return getService().registerUser((UserUserInteraction) map.get("userUserInteraction"));
    }
}
