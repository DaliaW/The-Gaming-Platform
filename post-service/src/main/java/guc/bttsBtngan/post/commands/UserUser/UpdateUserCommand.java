package guc.bttsBtngan.post.commands.UserUser;

import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public class UpdateUserCommand extends UserUserCommand{

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        System.out.println(" IN UPDATEEEEEEEEEEEEEEEEEEEEEEE");
        return getService().updateUser((Long)map.get("user_id"), (String)map.get("new_username"),
                (String)map.get("new_email"),(String)map.get("oldPassword")
                ,(String)map.get("newPassword"),(MultipartFile) map.get("photo"));
    }
}
