package guc.bttsBtngan.chat.commands.privateChat;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class DeletePrivateMessageCommand extends PrivateChatCommand{
	
	@Override
	public Object execute(HashMap<String, Object> body) throws Exception{
		return getService().deletePrivateMessage((String) body.get("user_id"), (String) body.get("private_id"), (String) body.get("message_id"));
	}

}
