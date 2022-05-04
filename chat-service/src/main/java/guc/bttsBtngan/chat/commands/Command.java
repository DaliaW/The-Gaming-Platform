package guc.bttsBtngan.chat.commands;

import java.util.HashMap;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Command {
	
	public abstract Object execute(HashMap<String, Object> map) throws Exception;
}
