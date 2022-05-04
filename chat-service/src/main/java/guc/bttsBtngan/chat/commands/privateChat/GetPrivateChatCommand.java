package guc.bttsBtngan.chat.commands.privateChat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class GetPrivateChatCommand extends PrivateChatCommand{
	
	@Override
	public Object execute(HashMap<String, Object> body) throws Exception{
		return getService().getPrivateChat((String) body.get("user_id"), (String) body.get("private_id"));
	}

}
