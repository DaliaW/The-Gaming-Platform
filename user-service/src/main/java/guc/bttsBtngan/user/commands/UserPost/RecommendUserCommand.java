package guc.bttsBtngan.user.commands.UserPost;

import org.springframework.stereotype.Component;

import java.util.HashMap;
@Component
public class RecommendUserCommand extends UserPostCommand{

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return getService().userRecommendations((String) map.get("user_id"));
    }
}