package fia.ues.edu.siam.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import fia.ues.edu.siam.Services.impl.AnimalServiceImpl;
import fia.ues.edu.siam.Services.impl.CategoriaServiceImpl;
import fia.ues.edu.siam.Services.impl.EmpresaServiceImp;
import fia.ues.edu.siam.Services.impl.ImagenServiceImpl;
import fia.ues.edu.siam.Services.impl.MensajeServiceImpl;
import fia.ues.edu.siam.Services.impl.PublicacionServiceImpl;
import fia.ues.edu.siam.Services.impl.SolicitudAdopcionServiceImpl;
import fia.ues.edu.siam.Services.impl.UserRoleServiceImpl;
import fia.ues.edu.siam.Services.impl.UserServiceImpl;
import fia.ues.edu.siam.entity.Animal;
import fia.ues.edu.siam.entity.Empresa;
import fia.ues.edu.siam.entity.Mensaje;
import fia.ues.edu.siam.entity.Users;
import fia.ues.edu.siam.entity.UserRole;


//https://www.iteramos.com/pregunta/32534/como-comprobar-hasrole-en-codigo-java-con-spring-security

@Controller
public class IndexController {
	
	@Autowired
	@Qualifier("solicitudAdopcionServiceImpl")
	private SolicitudAdopcionServiceImpl solicitudAdopcionServiceImpl;
	
	@Autowired
	@Qualifier("mensajeServiceImpl")
	private MensajeServiceImpl mensajeServiceImpl;
	
	@Autowired
	@Qualifier("publicacionServiceImpl")
	private PublicacionServiceImpl publicacionServiceImpl;
	
	@Autowired
	@Qualifier("categoriaServiceImpl")
	private CategoriaServiceImpl categoriaServiceImpl;
	
	@Autowired
	@Qualifier("animalServiceImpl")
	private AnimalServiceImpl animalServiceImpl;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserServiceImpl userService;
	
	
	@GetMapping("/")
	public String index(Model model) {
		String retornar = "/errores/";
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("super_user"))) {
					retornar = "redirect:/empresa/editar";
				}else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin_role"))) {
					retornar = "redirect:/indexAdmin";
				}else {
					retornar = "redirect:/index/last/1/10";
				}
			}

		}else {
			retornar = "redirect:/index";
		}				
		return retornar;
	}
	
	@PostMapping("/index/buscar")
	public String buscar(Model model, @ModelAttribute("buscar") String buscar) {
		String retornar = "redirect:/";
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null && buscar!=null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("user_role"))) {
					int num_users = userService.cantidad_users();
					int num_admin = userService.cantidad_admin();
					float porcentaje = ((float) num_admin / (float) num_users) *100;
					model.addAttribute("num_users", num_users);
					model.addAttribute("porcentaje_user", porcentaje);
					List<Animal> animales = animalServiceImpl.findAllValid(1);
					int num_categoria = categoriaServiceImpl.findAll().size();
					int animal_disponible = animales.size();
					int animal_total = animalServiceImpl.findAll().size();
					int animal_adoptado = animal_total - animal_disponible;	
					model.addAttribute("animal_adoptado",animal_adoptado);
					model.addAttribute("num_categoria",num_categoria);
					model.addAttribute("num_animal_disp",animal_disponible);
					model.addAttribute("num_animal",animal_total);
					
					int solicitudes = solicitudAdopcionServiceImpl.findAllSAdopcionAcceptd(0).size();
					int aceptadas = solicitudAdopcionServiceImpl.findAllSAdopcionAcceptd(1).size();
					int rechazadas = solicitudAdopcionServiceImpl.findAllSAdopcionAcceptd(2).size();

					model.addAttribute("solicitudes",solicitudes);
					model.addAttribute("aceptadas",aceptadas);
					model.addAttribute("rechazadas",rechazadas);
					int paginacion= animal_disponible/5 + 1;
					List<Integer> paginaciones = new ArrayList<Integer>();
					for(int i=1;i<=paginacion;i++) {
						paginaciones.add(i*5);
					}
					org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
					model.addAttribute("username", user.getUsername());
					Users user_valid = userService.findUserByUsername(user.getUsername());
					model.addAttribute("id_user",user_valid.getId());

					Users user_to = userService.findUserByFirstAdminRole();
					model.addAttribute("enviar", user_to.getId());
					model.addAttribute("enviar_user", user_to.getUsername());
					Users contador = userService.findUserCount(user_valid.getId());
					model.addAttribute("contador", contador.getCantidad());
					
					
					
					model.addAttribute("paginaciones", paginaciones);
					model.addAttribute("animales", animalServiceImpl.findAll(buscar));
					System.out.println(buscar+" "+animalServiceImpl.findAll(buscar));
					retornar = "HomeUser";
				}
			}

		}				
		return retornar;
	}
	
	@GetMapping("/index/last/{limite_inf}/{limite_sup}")
	public String indexLast(Model model, @PathVariable("limite_inf") int limite,@PathVariable("limite_sup") int limite_sup ) {
		String retornar = "/errores/";
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("super_user"))) {
					retornar = "redirect:/";
				}else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin_role"))) {
					retornar = "redirect:/indexAdmin";
				}else {
					int num_users = userService.cantidad_users();
					int num_admin = userService.cantidad_admin();
					float porcentaje = ((float) num_admin / (float) num_users) *100;
					model.addAttribute("num_users", num_users);
					model.addAttribute("porcentaje_user", porcentaje);
					List<Animal> animales = animalServiceImpl.findAllValid(1);
					int num_categoria = categoriaServiceImpl.findAll().size();
					int animal_disponible = animales.size();
					int animal_total = animalServiceImpl.findAll().size();
					int animal_adoptado = animal_total - animal_disponible;	
					model.addAttribute("animal_adoptado",animal_adoptado);
					model.addAttribute("num_categoria",num_categoria);
					model.addAttribute("num_animal_disp",animal_disponible);
					model.addAttribute("num_animal",animal_total);
					
					int solicitudes = solicitudAdopcionServiceImpl.findAllSAdopcionAcceptd(0).size();
					int aceptadas = solicitudAdopcionServiceImpl.findAllSAdopcionAcceptd(1).size();
					int rechazadas = solicitudAdopcionServiceImpl.findAllSAdopcionAcceptd(2).size();

					model.addAttribute("solicitudes",solicitudes);
					model.addAttribute("aceptadas",aceptadas);
					model.addAttribute("rechazadas",rechazadas);
					int paginacion= animal_disponible/10 + 1;
					List<Integer> paginaciones = new ArrayList<Integer>();
					for(int i=1;i<=paginacion;i++) {
						paginaciones.add(i);
					}
					
					org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
					model.addAttribute("username", user.getUsername());
					Users user_valid = userService.findUserByUsername(user.getUsername());
					model.addAttribute("id_user",user_valid.getId());
					Users user_to = userService.findUserByFirstAdminRole();
					model.addAttribute("enviar", user_to.getId());
					model.addAttribute("enviar_user", user_to.getUsername());

					System.out.println("id : "+user_valid.getId());
					Users contador = userService.findUserCount(user_valid.getId());
					model.addAttribute("contador", contador.getCantidad());
					System.out.println("Cantidad: "+contador.getCantidad());
					
					model.addAttribute("paginaciones", paginaciones);
					model.addAttribute("animales", animalServiceImpl.findAllLastestValid(limite-1, limite_sup));
					retornar = "HomeUser";
				}
			}

		}else {
			retornar = "HomeUser";
		}				
		return retornar;
	}

	
	@GetMapping("/indexAdmin")
	public String indexAdmin(Model model) {
		String retornar = "/errores/";
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("super_user"))) {
					retornar = "redirect:/";
				}else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin_role"))) {
					retornar = "HomeAdmin";
				}else {
					retornar = "redirect:/index";
				}
			}

		}else {
			retornar = "redirect:/index";
		}
		model.addAttribute("animales", animalServiceImpl.findAllValid(1));
		return retornar;
	}
	

	@GetMapping("publicaciones_empresa")
	public String publicaciones_empresa(Model model) {
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("user_role"))) {
					int num_users = userService.cantidad_users();
					int num_admin = userService.cantidad_admin();
					float porcentaje = ((float) num_admin / (float) num_users) *100;
					model.addAttribute("num_users", num_users);
					model.addAttribute("porcentaje_user", porcentaje);
					List<Animal> animales = animalServiceImpl.findAllValid(1);
					int num_categoria = categoriaServiceImpl.findAll().size();
					int animal_disponible = animales.size();
					int animal_total = animalServiceImpl.findAll().size();
					int animal_adoptado = animal_total - animal_disponible;
					
					model.addAttribute("animal_adoptado",animal_adoptado);
					model.addAttribute("num_categoria",num_categoria);
					model.addAttribute("num_animal_disp",animal_disponible);
					model.addAttribute("num_animal",animal_total);
					
					org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
					model.addAttribute("username", user.getUsername());
					Users user_valid = userService.findUserByUsername(user.getUsername());
					model.addAttribute("id_user",user_valid.getId());

					Users user_to = userService.findUserByFirstAdminRole();
					model.addAttribute("enviar", user_to.getId());
					model.addAttribute("enviar_user", user_to.getUsername());
					
					Users contador = userService.findUserCount(user_valid.getId());
					model.addAttribute("contador", contador.getCantidad());
					
					model.addAttribute("publicaciones",publicacionServiceImpl.findAllValidate());
					return "publicacion/presentacion";					
				}
			}
		}
		
		return "redirect:/";
	}
	
	
	@GetMapping("/mensaje/{id}")
	public String getMessagesById(Model model, @PathVariable("id") int id) {
		String retornar = "mensajes/index";
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
				model.addAttribute("username", user.getUsername());
				Users user_valid = userService.findUserByUsername(user.getUsername());
				model.addAttribute("id_user",user_valid.getId());
				model.addAttribute("enviar", id);
				model.addAttribute("username_to", userService.findUserById(id).getUsername());
			}

		}
		return retornar;
	}
	
	@GetMapping("/mensajes")
	public String getPlantilla(Model model) {
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
				model.addAttribute("username", user.getUsername());
				Users user_valid = userService.findUserByUsername(user.getUsername());
				model.addAttribute("id_user",user_valid.getId());
				model.addAttribute("usuarios", userService.findAllNotI(user_valid.getId()));
				//model.addAttribute("enviar", 7);
				//model.addAttribute("username_to", userService.findUserById(7).getUsername());
			}

		}
		return "mensajes/plantilla";
	}
	
	
	
	@GetMapping("/contactar")
	public String getUsuarios(Model model) {
		String retornar = "mensajes/contactar";
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
					org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
					 model.addAttribute("username", user.getUsername());
					 Users user_valid = userService.findUserByUsername(user.getUsername());
					 model.addAttribute("id_user",user_valid.getId());
					 model.addAttribute("usuarios", userService.findAll(user_valid.getId()));
					 model.addAttribute("enviar", 10);
			}

		}
		return retornar;
	}
		
}
