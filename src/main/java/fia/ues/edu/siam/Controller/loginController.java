package fia.ues.edu.siam.Controller;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fia.ues.edu.siam.Services.impl.EmpresaServiceImp;
import fia.ues.edu.siam.Services.impl.ServicioServiceImpl;
import fia.ues.edu.siam.Services.impl.UserRoleServiceImpl;
import fia.ues.edu.siam.Services.impl.UserServiceImpl;
import fia.ues.edu.siam.entity.Empresa;
import fia.ues.edu.siam.entity.Servicios;
import fia.ues.edu.siam.entity.Users;
import fia.ues.edu.siam.entity.UserRole;


@Controller
public class loginController {
	private static final Log log = LogFactory.getLog(loginController.class);
	@Autowired
	@Qualifier("empresaServiceImpl")
	private EmpresaServiceImp empresaService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserServiceImpl userService;

	@Autowired
	@Qualifier("userRoleServiceImpl")
	private UserRoleServiceImpl roleService;

	@Autowired
	@Qualifier("servicioServiceImpl")
	private ServicioServiceImpl servicioServiceImpl;
	
	
	@GetMapping("/login")
	public String loginGet(Model model, @RequestParam(name="error", required=false) String error, @RequestParam(name="logout",required=false) String logout, @RequestParam(name="exito", required=false) String exito) {
		String p = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		log.info(p);
		if(!p.equalsIgnoreCase("anonymousUser")) {
			model.addAttribute("error", "Error, ya estas autenticado :v");
			return "home";
		}
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		model.addAttribute("exito", exito);
		
		int empresas = empresaService.countEmpresa();
		if(empresas<=0) {
			Empresa empresa = new Empresa("Nombre de la empresa", "EMP001", new Date(), "Descripcion de la empresa", "none", "99999-999-99-9", "empresa@empresa.com", "2222-2222");
			Empresa emp = empresaService.updateEmpresa(empresa);
			BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
			Users usuario = new fia.ues.edu.siam.entity.Users("superuser", pass.encode("root"), true);
			Users usr= userService.updateUser(usuario);
			UserRole role = new UserRole(usr, "super_user");
			UserRole rol = roleService.updateUserRole(role);
			usr.insertRole(rol);
			userService.updateUser(usr);
			servicioServiceImpl.updateService(new Servicios(empresa, "Adopcion", "Brindamos todas las facilidades para que adoptes tus mascotas", 1));
			servicioServiceImpl.updateService(new Servicios(empresa, "Cuidados", "Le damos a tus futuras mascotas el cuidado que se merecen", 1));
			servicioServiceImpl.updateService(new Servicios(empresa, "Mensajeria", "Puedes escribirnos en un chat privado especialmente para ti", 1));
			
		}
		return "gestion_usuario/login";
	}
	
	@GetMapping({"/loginsuccess"})
	public String loginCheck() { 
		log.info("Redireccionando al index");
		return "redirect:/";
	}

	@PreAuthorize("hasRole('user_role')")
	@GetMapping("/error403")
	public String getError() {
		return "home";
	}
	
	
	@GetMapping("/fonts/PT_Sans/PTSans-Bold.ttf")
	public String loginRedirectFont() {
		return "redirect:/";
	}
	
	@GetMapping("/fonts/PT_Sans/PTSans-Regular.ttf")
	public String loginRedirectFontsSans() {
		return "redirect:/";
	}
	
}
