package guc.bttsBtngan.post.commands;

import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class TagInCommentPostCommand extends PostCommand{
    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return getService().commentTagInPost((String)map.get("postId"),(String)map.get("commentId"),(String[])map.get("userIds"),(String)map.get("userIdSending"));
    }
}
