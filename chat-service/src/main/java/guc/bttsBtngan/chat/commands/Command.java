package guc.bttsBtngan.chat.commands;

import java.util.HashMap;

public abstract class Command {
	
	public abstract void execute(HashMap<String, Object> map);
}
