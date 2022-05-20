package guc.bttsBtngan.chat.commands.privateChat;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Component;

import guc.bttsBtngan.chat.data.PrivateChat;

@Component
public class CreatePrivateChatCommand extends PrivateChatCommand {
	

	@Override
	public Object execute(HashMap<String, Object> map) throws Exception {
		PrivateChat chat = new PrivateChat((String) map.get("user_id"), (String) map.get("user_2"));
		return getService().createPrivateChat(chat);
	}

}
