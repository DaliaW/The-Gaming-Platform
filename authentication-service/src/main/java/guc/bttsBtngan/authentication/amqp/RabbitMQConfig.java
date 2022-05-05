package guc.bttsBtngan.authentication.amqp;

import guc.bttsBtngan.authentication.commands.Command;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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




@Configuration
public class RabbitMQConfig {

    @Autowired
    private Map<String, Command> commands;
    @Autowired
    private AmqpTemplate amqpTemplate;
    private static final String request_queue = "authentication_req";
    private static final String reply_queue = "authentication_gateway";
    @Autowired
	private ExecutorService threadPool;

    @Bean(name = {request_queue})
    public Queue request_queue() {
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



	@Bean
	public ExecutorService executor() {

		return new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000));
	}

    @RabbitListener(queues = request_queue)
    public void listen(HashMap<String, Object> payload, @Headers Map<String, Object> headers) {
    	threadPool.submit(() -> {
        	HashMap<String, Object> map = new HashMap<>();
        	try {
        		System.out.println("started processing task: " + payload.get("content"));
    			Object res = commands.get((String)headers.get("command")).execute(payload);
    			map.put("data", res);
    		} catch (Exception e) {
    			map.put("error", e.getMessage());
    		} finally {
    			amqpTemplate.convertAndSend(reply_queue, map);
        		System.out.println("finished processing task: " + payload.get("content"));
    		}
    	});
    }

    // dummy method for testing delete
//    @Bean
//    public ApplicationRunner runner(AmqpTemplate template) {
//        return args -> {
//            for(int i = 0 ; i < 20; i++) {
//                Map<String, Object> map = new HashMap<>();
//
//                map.put("notificationID", "0VQssXEceOufv7hAUHqT");
//                map.put("userID", "id12142");
//
//                template.convertAndSend(request_queue, map, m -> {
//                    m.getMessageProperties().setHeader("command", "deleteNotificationCommand");
//                    return m;
//                });
//            }
//        };
//    }

    // dummy method for testing get
//    @Bean
//    public ApplicationRunner runner(AmqpTemplate template) {
//        return args -> {
//            for(int i = 0 ; i < 5; i++) {
//                Map<String, Object> map = new HashMap<>();
//
//                //    map.put("notificationID", "0VQssXEceOufv7hAUHqT");
//                map.put("userID", "id1015");
//
//                template.convertAndSend(request_queue, map, m -> {
//                    m.getMessageProperties().setHeader("command", "getNotificationCommand");
//                    return m;
//                });
//            }
//        };
//    }

}