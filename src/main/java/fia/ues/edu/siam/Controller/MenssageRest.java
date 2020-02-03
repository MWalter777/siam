package fia.ues.edu.siam.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fia.ues.edu.siam.Services.impl.MensajeServiceImpl;
import fia.ues.edu.siam.Services.impl.UserServiceImpl;
import fia.ues.edu.siam.entity.Mensaje;
import fia.ues.edu.siam.entity.User;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/service_rest")
public class MenssageRest {
	
	@Autowired
	@Qualifier("mensajeServiceImpl")
	private MensajeServiceImpl mensajeServiceImpl;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserServiceImpl userService;
	
	
	@GetMapping("/all_messages/{user_to}/{user_from}")
	public List<Mensaje> findAll_messages(Model model, @PathVariable("user_to") int user_to, @PathVariable("user_from") int user_from){
		mensajeServiceImpl.visto(user_to,user_from);
		return mensajeServiceImpl.findAllMessage(user_to, user_from);
	}
	
	
	@GetMapping("/all_user")
	public List<User> findAll_user(Model model){
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
				User user_valid = userService.findUserByUsername(user.getUsername());
				return userService.findAllNotIdWithCant(user_valid.getId());
			}

		}
		return null;
	}
	
	@GetMapping("/get_user/{username}/valor")
	public List<User> get_user(Model model, @PathVariable("username") String username){
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null && username!=null && !username.isEmpty()) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
				User user_valid = userService.findUserByUsername(user.getUsername());
				System.out.println("Se cumplio la condicion: "+username);
				return userService.findAll(username, user_valid.getId());
			}

		}
		return null;
	}
	
	
}
