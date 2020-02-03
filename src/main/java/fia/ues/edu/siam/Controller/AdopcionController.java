package fia.ues.edu.siam.Controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fia.ues.edu.siam.Services.impl.AnimalServiceImpl;
import fia.ues.edu.siam.Services.impl.MensajeServiceImpl;
import fia.ues.edu.siam.Services.impl.SolicitudAdopcionServiceImpl;
import fia.ues.edu.siam.Services.impl.UserServiceImpl;
import fia.ues.edu.siam.entity.Animal;
import fia.ues.edu.siam.entity.Mensaje;
import fia.ues.edu.siam.entity.SolicitudAdopcion;
import fia.ues.edu.siam.entity.Users;

@Controller
@RequestMapping("/adopcion")
public class AdopcionController {
	
	@Autowired
	@Qualifier("mensajeServiceImpl")
	private MensajeServiceImpl mensajeServiceImpl;
	
	@Autowired
	@Qualifier("solicitudAdopcionServiceImpl")
	private SolicitudAdopcionServiceImpl solicitudAdopcionServiceImpl;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserServiceImpl userService;
	
	@Autowired
	@Qualifier("animalServiceImpl")
	private AnimalServiceImpl animalServiceImpl;
	
	/*
	 * URL para ver todas las solicitudes
	 */
	@GetMapping("/index")
	public String index(Model model,@RequestParam(name="pendientes", required=false) String pendientes) {
		String retorno = "solicitud_adopcion/index";
		if(pendientes!=null) {
			model.addAttribute("solicitudes", solicitudAdopcionServiceImpl.findAllSAdopcionAcceptd(0));
		}
		else {
			model.addAttribute("solicitudes", solicitudAdopcionServiceImpl.findAll());
		}
		
		return retorno;
	}
	
	/*
	 * URL para ver mis solicitudes de mascotas y el estado
	 */

	@GetMapping("/mi_solicitud")
	public String mi_solicitud(Model model) {
		String retorno = "solicitud_adopcion/index";
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				try {
					org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
					Users usr = userService.findUserByUsername(user.getUsername());
					model.addAttribute("solicitudes", solicitudAdopcionServiceImpl.findAllSAdopcionByUser(usr.getId()));
					retorno = "solicitud_adopcion/index_user";
				} catch (Exception e) {
					return "redirect:/";
				}
			}
		}
		return retorno;
	}
	
	
	/*
	 * URL para solicitar un animal especifico
	 */
	@GetMapping("/solicitar/{id}")
	public String solicitar(Model model,@PathVariable("id") int id, @RequestParam(name="error", required=false) String error,@RequestParam(name="exito", required=false) String exito) {
		String retorno = "redirect:/";		
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				try {
					org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
					Users usr = userService.findUserByUsername(user.getUsername());
					SolicitudAdopcion solicitudAdopcion = solicitudAdopcionServiceImpl.findSAdopcionByUserLast(usr.getId());
					if(solicitudAdopcion!=null) {
						model.addAttribute("solicitud", solicitudAdopcion);
					}else {
						model.addAttribute("solicitud", new SolicitudAdopcion());
					}
					if(solicitudAdopcionServiceImpl.exists(id, usr.getId())>0) {
						model.addAttribute("existe","Ya hiciste esta solicitud! no puedes solicitarlo dos veces");
					}
					model.addAttribute("id_animal", id);
					model.addAttribute("animal", animalServiceImpl.findById(id));
					model.addAttribute("id_usuario", usr.getId());
					retorno = "solicitud_adopcion/solicitud";
				} catch (Exception e) {
					return "redirect:/?error=username";
				}
			}
		}
		
		
		return retorno;
	}
	
	/*
	 * Metodo post para guardar una solicitud, no la puede editar el usuario ni el admin
	 */
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("solicitud") SolicitudAdopcion solicitud,@ModelAttribute("id_animal") int id_animal,@ModelAttribute("id_usuario") int id_usuario) {
		Users user = userServiceImpl.findUserById(id_usuario);
		Animal animal = animalServiceImpl.findById(id_animal);
		String codigo = "S"+user.getId();
		solicitud.setCodigo_solicitud(codigo);
		solicitud.setEstado(0);
		solicitud.setAnimal(animal);
		solicitud.setUser(user);
		SolicitudAdopcion adopcion = solicitudAdopcionServiceImpl.updateSolicitud(solicitud);
		codigo = "S"+user.getId()+""+adopcion.getId_adopcion();
		adopcion.setCodigo_solicitud(codigo);
		solicitudAdopcionServiceImpl.updateSolicitud(adopcion);
		System.out.println(solicitud.toString()+" "+id_animal+" "+id_usuario+" "+codigo);
		String retorno="redirect:/";
		return retorno;
	}
	
	/*
	 * metodo post exclusivamente para aceptar la solicitud
	 */
	@PostMapping("/aceptar")
	public String aceptar(@ModelAttribute("id_adopcion") int id_adopcion) {
		SolicitudAdopcion sol = solicitudAdopcionServiceImpl.findSAdopcionById(id_adopcion);
		sol.setEstado(1);
		
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin_role"))) {
					org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
					Users user_valid = userService.findUserByUsername(user.getUsername()); //ya tengo mi propio usuario
					String contenido = "Gracias por su paciencia, nos complace en notificarle que se ha aceptado su solicitud, por favor ponerse en contacto a trave de este chat para iniciar el proceso";
			    	mensajeServiceImpl.updateMensaje(new Mensaje(sol.getUser(), user_valid, contenido, new Date()));
				}
			}
		}
		
		
		
		
		
		solicitudAdopcionServiceImpl.updateSolicitud(sol);
		String retorno="redirect:/adopcion/solicitud_especifica/"+id_adopcion+"?exito";
		return retorno;		
	}
	
	/*
	 * Metodo post exclusivamente para denegar la solcitud
	 */
	@PostMapping("/denegar")
	public String denegar(@ModelAttribute("id_adopcion") int id_adopcion) {
		SolicitudAdopcion sol = solicitudAdopcionServiceImpl.findSAdopcionById(id_adopcion);
		sol.setEstado(2);

		
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin_role"))) {
					org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
					Users user_valid = userService.findUserByUsername(user.getUsername()); //ya tengo mi propio usuario
					String contenido = "Gracias por su paciencia, Lamentamos informarle que su solicitud fue rechazada, para mas informacion escribanos al inbox";
			    	mensajeServiceImpl.updateMensaje(new Mensaje(sol.getUser(), user_valid, contenido, new Date()));
				}
			}
		}

		
		
		solicitudAdopcionServiceImpl.updateSolicitud(sol);
		String retorno="redirect:/adopcion/solicitud_especifica/"+id_adopcion+"?rechazado";
		return retorno;		
	}
	
	/*
	 * URL para ver una solicitud especifica, el usuario de la persona que lo solicita y todas sus solicitudes anteriores
	 */
	@GetMapping("/solicitud_especifica/{id}")
	public String solicitud_especifica(Model model,@PathVariable("id") int id) {
		String retornar = "redirect:/";

		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin_role"))) {
					retornar="solicitud_adopcion/solicitud_expecifica";
				}
			}
		}else {
			retornar = "redirect:/login";
		}
		
		model.addAttribute("solicitud",solicitudAdopcionServiceImpl.findSAdopcionById(id));
		return retornar;
	}
	
	
}
