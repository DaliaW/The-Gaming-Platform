package guc.bttsBtngan.chat.amqp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;

import guc.bttsBtngan.chat.commands.Command;
import guc.bttsBtngan.chat.data.ChatMessage;


@Configuration
public class RabbitMQConfig {
	
	private static final String request_queue = "messaging_req";
	@Autowired
	private Map<String, Command> commands;
	
	@Bean
	public Queue messaging_req() {
//		Map<String, Object> arguments = new HashMap<String, Object>();
//	    arguments.put("x-single-active-consumer", true);
//	    return new Queue(request_queue, true, false, false, arguments);
		return new Queue(request_queue);
	}
	
	@Bean
	public MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}
	
    @RabbitListener(queues = request_queue)
    public void listen(HashMap<String, Object> payload, @Header("command") String command) {
//    	System.out.println(Thread.currentThread().getName());
//    	System.out.println((String)payload.get("content") + " started!!");
    	commands.get(command).execute(payload);
//    	System.out.println((String)payload.get("content") + " ended!!");

    }
    
    // dummy method for testing
    @Bean
    public ApplicationRunner runner(AmqpTemplate template) {
        return args -> {
        	for(int i = 0 ; i < 10; i++) {
            	Map<String, Object> map = new HashMap<>();
            	map.put("user_id", "user_1");
            	map.put("group_id", "qNjVI5EPodNC5UHQnbF2");
            	map.put("content", "message " + i);
            	template.convertAndSend(request_queue, map, m -> {
                	m.getMessageProperties().setHeader("command", "sendGroupMessageCommand");
                	return m;
                });
        	}
        };
    }
	
}
