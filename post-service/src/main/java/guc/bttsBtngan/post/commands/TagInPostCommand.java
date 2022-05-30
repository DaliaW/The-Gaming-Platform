package guc.bttsBtngan.post.commands;

import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class TagInPostCommand extends PostCommand{
    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return getService().tagInPost((String)map.get("postId"),(String[])map.get("userIds"),(String)map.get("user_id"));
    }
}
