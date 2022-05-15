package guc.bttsBtngan.post.commands;

import java.util.HashMap;

public class DelTagInPostCommand extends PostCommand{
    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return getService().delTagInPost((String)map.get("postId"),(String[])map.get("userIds"));
    }
}
