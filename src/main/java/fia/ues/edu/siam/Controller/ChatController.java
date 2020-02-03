package fia.ues.edu.siam.Controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import fia.ues.edu.siam.Services.impl.MensajeServiceImpl;
import fia.ues.edu.siam.Services.impl.UserServiceImpl;
import fia.ues.edu.siam.entity.ChatMessage;
import fia.ues.edu.siam.entity.Mensaje;
import fia.ues.edu.siam.entity.User;

@Controller
public class ChatController {
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserServiceImpl userService;
	
	@Autowired
	@Qualifier("mensajeServiceImpl")
	private MensajeServiceImpl mensajeServiceImpl;

    @MessageMapping("/chat.sendMessage/{id}")
    @SendTo("/topic/{id}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, @DestinationVariable("id") int id) {
    	User user_to = userService.findUserById(id);
    	User user_from = userService.findUserById(Integer.parseInt(chatMessage.getUser_from()));
    	String contenido = chatMessage.getContent();
    	mensajeServiceImpl.updateMensaje(new Mensaje(user_to, user_from, contenido, new Date()));
    	mensajeServiceImpl.visto(user_to.getId(),user_from.getId());
        return chatMessage;
    }

    @MessageMapping("/chat.addUser/{id}")
    @SendTo("/topic/{id}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor, @DestinationVariable("id") int id) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        System.out.println("enviando mensaje de union a "+id);
        return chatMessage;
    }
    

}
