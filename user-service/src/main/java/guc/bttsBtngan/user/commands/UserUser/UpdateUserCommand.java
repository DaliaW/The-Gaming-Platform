package guc.bttsBtngan.user.commands.UserUser;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Component
public class UpdateUserCommand extends UserUserCommand{

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        String user_id=(String)(map.get("user_id"));
        String new_username=map.containsKey("username")?(String)(map.get("username")):null;
        String email=map.containsKey("email")?(String)(map.get("email")):null;
        String oldPassword=map.containsKey("oldPassword")?(String)(map.get("oldPassword")):null;
        String newPassword=map.containsKey("newPassword")?(String)(map.get("newPassword")):null;
        MultipartFile photo=map.containsKey("photo")?(MultipartFile)(map.get("photo")):null;
        return getService().updateUser(user_id,new_username,email,oldPassword,newPassword,photo);

    }
}
