package guc.bttsBtngan.post.commands;

import java.util.HashMap;

public class SearchPostCommand extends PostCommand{

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return getService().searchPosts((String)map.get("content"));
    }
}
