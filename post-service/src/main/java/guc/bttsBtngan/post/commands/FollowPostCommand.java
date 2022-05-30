package guc.bttsBtngan.post.commands;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class FollowPostCommand extends PostCommand{

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
    	return getService().followPost((String)map.get("user_id"),
        		(String)map.get("postId"),  
        		(boolean)map.get("follow"));
    }
}
