package fia.ues.edu.siam.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import fia.ues.edu.siam.Services.impl.UserRoleServiceImpl;
import fia.ues.edu.siam.Services.impl.UserService;
import fia.ues.edu.siam.Services.impl.UserServiceImpl;
import fia.ues.edu.siam.entity.Empresa;
import fia.ues.edu.siam.entity.Users;
import fia.ues.edu.siam.entity.UserRole;

@Controller
@RequestMapping("/register")
public class RegisterController {
	private static final Log log = LogFactory.getLog(RegisterController.class);
	@Autowired
	@Qualifier("userServiceImpl")
	private UserServiceImpl userService;

	@Autowired
	@Qualifier("userRoleServiceImpl")
	private UserRoleServiceImpl roleService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userServiceLog;
	
	@GetMapping("/register")
	public ModelAndView getRegister(@RequestParam(name="error", required=false) String error) {
		ModelAndView mav = new ModelAndView("gestion_usuario/register");
		String p = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		log.info(p);
		if(!p.equalsIgnoreCase("anonymousUser")) {
			mav.addObject("error", "Error, ya estas autenticado :v");
			mav.setViewName("home");
			return mav;
		}
		mav.addObject("error", error);
		mav.addObject("usuario", new Users());
		Date fecha = new Date();
		int anio = fecha.getYear() - 17 ;
		fecha.setYear(anio);
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		String fecha_actual = formateador.format(fecha);
		System.out.println(fecha_actual);
		mav.addObject("fecha_actual", fecha_actual);
		
		return mav;
	}
	
	@PostMapping("/save")
	public String getRegisterSave(@Valid @ModelAttribute("usuario") Users usuario,@ModelAttribute("password") String password,@ModelAttribute("fecha") String fecha ) {
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		Date fecha_nacimiento = null;
		try {
			fecha_nacimiento = formateador.parse(fecha);
		} catch (ParseException e) {
			log.error("Fecha incorrecta");
			return "redirect:/register/register?error=fecha";
		}
		log.info(usuario.getFecha_nacimiento() +"password " + password + " fecha: "+fecha_nacimiento);			
		usuario.setFecha_nacimiento(fecha_nacimiento);
		BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
		usuario.setPassword(pass.encode(password));
		try {
			Users user = userService.updateUser(usuario);
			UserRole user_role = new UserRole(usuario,"user_role");
			UserRole role = roleService.updateUserRole(user_role);
			user.insertRole(role);
			user.setEnabled(true);
			userService.updateUser(user);
			/*
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("user_role"));
			Authentication auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
			SecurityContextHolder.getContext().setAuthentication(auth);
			 */
			return "redirect:/login?exito";
		} catch (Exception e) {
			return "redirect:/register/register?error=usuario";
		}
	}
	
	@GetMapping("/miperfil")
	public String miPerfil() {
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				try {
					org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
					Users usr = userService.findUserByUsername(user.getUsername());
					return "redirect:/register/editar/"+usr.getId();
				} catch (Exception e) {
					return "redirect:/?error=username";
				}
			}
		}
		return "redirect:/";
	}
	
	
	
	@GetMapping("/registerAdmin")
	public ModelAndView getRegisterAdmin(@RequestParam(name="error", required=false) String error,@RequestParam(name="exito", required=false) String exito) {
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("super_user"))) {
					ModelAndView mav = new ModelAndView("/gestion_usuario/register_admin");
					String p = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
					mav.addObject("usuario", new Users());
					Date fecha = new Date();
					int anio = fecha.getYear() - 17 ;
					fecha.setYear(anio);
					SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
					String fecha_actual = formateador.format(fecha);
					System.out.println(fecha_actual);
					mav.addObject("fecha_actual", fecha_actual);
					mav.addObject("error", error);
					mav.addObject("exito", exito);
					return mav;
				}
			}
		}
		return new ModelAndView("redirect:/");
	}

	@PostMapping("/saveAdmin")
	public String getRegisterSaveAdmin(@Valid @ModelAttribute("usuario") Users usuario,@ModelAttribute("password") String password,@ModelAttribute("fecha") String fecha ) {
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		Date fecha_nacimiento = null;
		try {
			fecha_nacimiento = formateador.parse(fecha);
		} catch (ParseException e) {
			log.error("Fecha incorrecta");
			return "redirect:/register/register";
		}
		log.info(usuario.getFecha_nacimiento() +"password " + password + " fecha: "+fecha_nacimiento);			
		usuario.setFecha_nacimiento(fecha_nacimiento);
		BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
		usuario.setPassword(pass.encode(password));
		try {
			Users user = userService.updateUser(usuario);
			UserRole user_role = new UserRole(usuario,"admin_role");
			UserRole role = roleService.updateUserRole(user_role);
			user.insertRole(role);
			user.setEnabled(true);
			userService.updateUser(user);
			return "redirect:/register/registerAdmin?exito";
		} catch (Exception e) {
			return "redirect:/register/registerAdmin?error";
		}
	}
	
	@GetMapping("/admin")
	public String getAdmins(Model model) {
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("super_user"))) {
					List<UserRole> roles = roleService.findUserRoleAdmin();
					model.addAttribute("roles", roles);
					return "gestion_usuario/administradores";				}
			}
		}
		return "redirect:/";
	}
	
	@GetMapping("/editar/{id}")
	public String getAdminEdit(Model model,@PathVariable("id") int id, @RequestParam(name="error", required=false) String error) {
		Users user = userService.findUserById(id);
		model.addAttribute("user",user);
		model.addAttribute("error",error);
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				try {
					if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("user_role"))) {
						org.springframework.security.core.userdetails.User user_validate = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
						Users usr = userService.findUserByUsername(user_validate.getUsername());
						if(id==usr.getId()) return "gestion_usuario/editar_user";
						else return "redirect:/";
					}
					else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("super_user"))){
						return "gestion_usuario/editar_admin";
					}
					else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin_role"))){
						return "gestion_usuario/editar_admin";
					}
				} catch (Exception e) {
					return "redirect:/?error=username";
				}
			}
		}
		return "redirect:/?error=username";
		
	}
	

	@PostMapping("/editAdmin")
	public String getRegisterEditAdmin(@Valid @ModelAttribute("user") Users usuario,@ModelAttribute("password") String password,@ModelAttribute("fecha") String fecha ) {
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		Date fecha_nacimiento = null;
		try {
			fecha_nacimiento = formateador.parse(fecha);
		} catch (ParseException e) {
			log.error("Fecha incorrecta");
			return "redirect:/register/admin";
		}			
		usuario.setFecha_nacimiento(fecha_nacimiento);
		try {
			Users usr = userService.findUserById(usuario.getId());
			usuario.setFecha_nacimiento(fecha_nacimiento);
			usuario.setEnabled(true);
			if (password.isEmpty()) {
				usuario.setPassword(usr.getPassword());
			}else {
				BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
				usuario.setPassword(pass.encode(password));
			}
			userService.updateUser(usuario);
			log.info("usuario guardado: "+usuario.getId());
		} catch (Exception e) {
			return "redirect:/register/editar/"+usuario.getId()+"?error";
		}
		return "redirect:/register/admin";
	}
	
}
