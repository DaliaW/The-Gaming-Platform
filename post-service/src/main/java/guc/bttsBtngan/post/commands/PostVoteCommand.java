package guc.bttsBtngan.post.commands;

import java.util.HashMap;

public class PostVoteCommand extends PostCommand{

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return getService().postVote((String)map.get("userId"),
        		(String)map.get("postId"),  
        		(boolean)map.get("vote"));
    }
}
