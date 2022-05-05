package guc.bttsBtngan.authentication.commands;
import java.util.HashMap;

public abstract  class Command {

    public abstract Object execute(HashMap<String, Object> map)throws Exception;

}