package guc.bttsBtngan.chat.commands.privateChat;

import org.springframework.beans.factory.annotation.Autowired;
import guc.bttsBtngan.chat.commands.Command;
import guc.bttsBtngan.chat.services.PrivateChatService;

public abstract class PrivateChatCommand extends Command{
	
	private PrivateChatService service;
	
	public PrivateChatService getService() {
		return service;
	}

	@Autowired
	public final void setService(PrivateChatService service) {
		this.service = service;
	}
	
	
	
}