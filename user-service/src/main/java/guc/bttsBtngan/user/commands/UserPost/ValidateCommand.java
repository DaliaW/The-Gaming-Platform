package guc.bttsBtngan.user.commands.UserPost;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ValidateCommand extends UserPostCommand {


    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return getService().validate((String)map.get("user_id"));
    }
}