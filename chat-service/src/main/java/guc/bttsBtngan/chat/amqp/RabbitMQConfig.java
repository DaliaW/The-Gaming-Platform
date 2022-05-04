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
import org.springframework.messaging.handler.annotation.Headers;

import guc.bttsBtngan.chat.commands.Command;


@Configuration
public class RabbitMQConfig {
	
	@Autowired
	private Map<String, Command> commands;
	@Autowired
	private AmqpTemplate amqpTemplate;
	private static final String request_queue = "messaging_req";
	private static final String reply_queue = "gateway";
	
	@Bean(name = {request_queue})
	public Queue request_queue() {
//		Map<String, Object> arguments = new HashMap<String, Object>();
//	    arguments.put("x-single-active-consumer", true);
//	    return new Queue(request_queue, true, false, false, arguments);
		return new Queue(request_queue);
	}
	
	@Bean(name = {reply_queue})
	public Queue reply_queue() {
		return new Queue(reply_queue);
	}
	
	@Bean
	public MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}
	
    @RabbitListener(queues = request_queue)
    public void listen(HashMap<String, Object> payload, @Headers Map<String, Object> headers) {
    	HashMap<String, Object> map = new HashMap<>();
    	try {
			Object res = commands.get((String)headers.get("command")).execute(payload);
			map.put("data", res);
		} catch (Exception e) {
			map.put("error", e.getMessage());
		} finally {
			amqpTemplate.convertAndSend(reply_queue, map);
		}
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
