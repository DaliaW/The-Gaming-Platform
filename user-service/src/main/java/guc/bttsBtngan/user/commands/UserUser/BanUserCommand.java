package guc.bttsBtngan.user.commands.UserUser;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class BanUserCommand extends UserUserCommand {
    @Override
    public Object execute(HashMap<String, Object> map) {
        return getService().banUser((String) map.get("userId"), (String) map.get("userToBan"));
    }
}
