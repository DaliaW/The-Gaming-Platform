package guc.bttsBtngan.post.commands;

import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class CommentPostCommand extends PostCommand{

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return getService().commentPost((String)map.get("user_id"),
        		(String)map.get("postId"),  
        		(String)map.get("comment"));
    }
}
