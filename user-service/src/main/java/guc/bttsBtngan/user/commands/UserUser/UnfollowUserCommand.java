package guc.bttsBtngan.user.commands.UserUser;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class UnfollowUserCommand extends UserUserCommand{

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return  getService().unfollowUser((String)map.get("user_id"),(String)map.get("userToUnfollowId"));
    }
}