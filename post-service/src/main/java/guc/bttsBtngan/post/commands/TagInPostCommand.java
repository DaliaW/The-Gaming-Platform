package guc.bttsBtngan.post.commands;

import java.util.HashMap;

public class TagInPostCommand extends PostCommand{
    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return getService().tagInPost((String)map.get("postId"),(String[])map.get("userIds"));
    }
}
