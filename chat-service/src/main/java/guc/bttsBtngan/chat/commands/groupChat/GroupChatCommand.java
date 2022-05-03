package guc.bttsBtngan.chat.commands.groupChat;

import org.springframework.beans.factory.annotation.Autowired;

import guc.bttsBtngan.chat.commands.Command;
import guc.bttsBtngan.chat.services.GroupChatService;

public abstract class GroupChatCommand extends Command{
	
	@Autowired
	GroupChatService service;
	
}
