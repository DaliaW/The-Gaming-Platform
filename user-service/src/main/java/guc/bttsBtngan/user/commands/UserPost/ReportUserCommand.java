package guc.bttsBtngan.user.commands.UserPost;


import org.springframework.stereotype.Component;
import java.util.HashMap;

@Component
public class ReportUserCommand extends UserPostCommand {


    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return getService().reportUser((String)map.get("userId"),(String)map.get("userId2"),(String)map.get("reportComment"));
    }
}