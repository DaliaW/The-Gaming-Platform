//package guc.bttsBtngan.post.amqp;
//
//import com.rabbitmq.client.Command;
//import guc.bttsBtngan.user.commands.Command;
//
//import java.util.*;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.handler.annotation.Headers;
//
//
//
//
//@Configuration
//public class RabbitMQConfig {
//
//    @Autowired
//    private Map<String, Command> commands;
//    @Autowired
//    private AmqpTemplate amqpTemplate;
//    private static final String request_queue = "authentication_req";
//    @Autowired
//    private ExecutorService threadPool;
//
//    @Bean(name = {request_queue})
//    public Queue request_queue() {
//        return new Queue(request_queue);
//    }
//
//    @Bean
//    public MessageConverter converter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//
//
//    @Bean
//    public ExecutorService executor() {
//
//        return new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000));
//    }
//
//    //    @RabbitListener(queues = request_queue)
////    public void listen(HashMap<String, Object> payload, @Headers Map<String, Object> headers) {
////    	threadPool.submit(() -> {
////        	HashMap<String, Object> map = new HashMap<>();
////        	try {
////        		System.out.println("started processing task: " + payload.get("content"));
////        		payload.put("user_id", headers.get("user_id"));
////				payload.put("timestamp", headers.get("timestamp"));
////    			Object res = commands.get((String)headers.get("command")).execute(payload);
////    			map.put("data", res);
////    		} catch (Exception e) {
////    			map.put("error", e.getMessage());
////    		} finally {
////    			amqpTemplate.convertAndSend((String) headers.get("amqp_replyTo"), map, m -> {
////    	        	m.getMessageProperties().setCorrelationId((String) headers.get("amqp_correlationId"));
////    	        	m.getMessageProperties().setReplyTo((String) headers.get("amqp_replyTo"));
////    	        	return m;
////    			});
////        		System.out.println("finished processing task: " + payload.get("content"));
////    		}
////    	});
////    }
//
//    @RabbitListener(queues = request_queue)
//    public void listen_2(HashMap<String, Object> payload, @Headers Map<String, Object> headers) {
//        HashMap<String, Object> map = new HashMap<>();
//        try {
//            payload.put("user_id", headers.get("user_id"));
//            Object res = commands.get((String)headers.get("command")).execute(payload);
//            map.put("data", res);
//        } catch (Exception e) {
//            map.put("error", e.getMessage());
//        } finally {
//            amqpTemplate.convertAndSend((String) headers.get("amqp_replyTo"), map, m -> {
//                m.getMessageProperties().setCorrelationId((String) headers.get("amqp_correlationId"));
//                m.getMessageProperties().setReplyTo((String) headers.get("amqp_replyTo"));
//                return m;
//            });
//        }
//    }
//
//    // dummy method for testing
////    @Bean
////    public ApplicationRunner runner(AmqpTemplate template) {
////        return args -> {
////        	for(int i = 0 ; i < 20; i++) {
////            	Map<String, Object> map = new HashMap<>();
////            	map.put("user_id", "user_1");
////            	map.put("group_id", "qNjVI5EPodNC5UHQnbF2");
////            	map.put("content", "message " + i);
////            	template.convertAndSend(request_queue, map, m -> {
////                	m.getMessageProperties().setHeader("command", "sendGroupMessageCommand");
////                	m.getMessageProperties().setReplyTo(reply_queue);
////                	m.getMessageProperties().setCorrelationId(UUID.randomUUID().toString());
////                	return m;
////                });
////        	}
////        };
////    }
//
//}