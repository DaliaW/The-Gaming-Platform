package guc.bttsBtngan.post.commands;

import java.util.HashMap;

public class AssignModeratorCommand extends PostCommand{

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return getService().assignModerator((String)map.get("postId"), (String)map.get("userId"));
    }
}
