package guc.bttsBtngan.user.commands.UserPost;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class BlockUserCommand extends UserPostCommand {


    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return getService().blockUser((String)map.get("user_id"), (String)map.get("otherId"));
    }
}