package guc.bttsBtngan.user.commands.UserUser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;

@Component
public class DeleteProfilePicCommand extends UserUserCommand{

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        String user_id=(String)(map.get("user_id"));
        return getService().deleteProfilePicture(user_id);

    }
}

