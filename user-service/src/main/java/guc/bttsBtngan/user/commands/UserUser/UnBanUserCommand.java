package guc.bttsBtngan.user.commands.UserUser;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class UnBanUserCommand extends UserUserCommand {

    @Override
    public Object execute(HashMap<String, Object> map){
        return getService().unbanUser((String)map.get("user_id"), (String)map.get("userToBan"));
    }
}
