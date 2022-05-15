package guc.bttsBtngan.user.commands.UserUser;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
@Component
public class FollowUserCommand extends UserUserCommand{

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return  getService().followUser((String)map.get("user_id"),(String)map.get("userToFollowId"));
    }
}