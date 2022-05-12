package guc.bttsBtngan.user.commands.UserUser;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
@Component
public class UpdateUserCommand extends UserUserCommand{

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return getService().updateUser((String)map.get("user_id"), (String)map.get("new_username"),
                (String)map.get("new_email"),(String)map.get("oldPassword")
                ,(String)map.get("newPassword"),(MultipartFile) map.get("photo"));
    }
}
