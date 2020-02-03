package fia.ues.edu.siam.SocketsEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import fia.ues.edu.siam.Services.impl.UserServiceImpl;
import fia.ues.edu.siam.entity.ChatMessage;
import fia.ues.edu.siam.entity.User;

@Component
public class WebSocketEventListener {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserServiceImpl userService;
	
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            logger.info("User Disconnected : " + username);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatMessage.setSender(username);
            
            User user_to = userService.findUserByUsername(username);
            System.out.println("Type: "+chatMessage.getType());
            System.out.println("Contenido: "+chatMessage.getContent());
            System.out.println("to: "+chatMessage.getSender()+" id: "+user_to.getId());
            
            messagingTemplate.convertAndSend("/topic/", chatMessage);
        }
    }
	
	
}
