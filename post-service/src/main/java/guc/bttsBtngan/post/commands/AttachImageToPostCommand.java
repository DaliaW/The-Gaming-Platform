package guc.bttsBtngan.post.commands;

import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AttachImageToPostCommand extends PostCommand{

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return getService().attachImageToPost((String)map.get("userId"),
        		(String)map.get("postId"),  
        		(MultipartFile)map.get("photo"));
    }
}
