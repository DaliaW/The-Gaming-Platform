package guc.bttsBtngan.chat.commands.groupChat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guc.bttsBtngan.chat.commands.Command;
import guc.bttsBtngan.chat.services.GroupChatService;

@Service
public abstract class GroupChatCommand extends Command{
	
	@Autowired
	GroupChatService service;
	
}
