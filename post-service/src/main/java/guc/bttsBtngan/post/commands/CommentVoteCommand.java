package guc.bttsBtngan.post.commands;

import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class CommentVoteCommand extends PostCommand{

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return getService().commentVote((String)map.get("userId"),
        		(String)map.get("postId"),
        		(String)map.get("commentId"),
        		(boolean)map.get("vote"));
    }
}
