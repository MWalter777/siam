package fia.ues.edu.siam.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.multipart.MultipartFile;

import fia.ues.edu.siam.Services.impl.ComentarioServiceImpl;
import fia.ues.edu.siam.Services.impl.EmpresaServiceImp;
import fia.ues.edu.siam.Services.impl.FileServiceImpl;
import fia.ues.edu.siam.Services.impl.ImagenServiceImpl;
import fia.ues.edu.siam.Services.impl.PublicacionServiceImpl;
import fia.ues.edu.siam.Services.impl.UserServiceImpl;
import fia.ues.edu.siam.entity.Animal;
import fia.ues.edu.siam.entity.Comentario;
import fia.ues.edu.siam.entity.Imagen;
import fia.ues.edu.siam.entity.Publicacion;
import fia.ues.edu.siam.entity.Users;

@Controller
@RequestMapping("/publicacion")
public class PublicacionController {
	
	@Autowired
	@Qualifier("imagenServiceImpl")
	private ImagenServiceImpl imagenServiceImpl;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserServiceImpl userService;
	
	@Autowired
	@Qualifier("fileServiceImpl")
	private FileServiceImpl fileService;
	
	@Autowired
	@Qualifier("publicacionServiceImpl")
	private PublicacionServiceImpl publicacionServiceImpl;
	
	@Autowired
	@Qualifier("empresaServiceImpl")
	private EmpresaServiceImp empresaServiceImp;
	
	@Autowired
	@Qualifier("comentarioServiceImpl")
	private ComentarioServiceImpl comentarioServiceImpl;
	

	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("publicaciones", publicacionServiceImpl.findAll());
		return "/publicacion/index";
	}
	
	@GetMapping("/create")
	public String getCreate(Model model, @RequestParam(name="error", required=false) String error) {
		model.addAttribute("publicacion", new Publicacion());
		return "/publicacion/create";
	}
	
	@GetMapping("/edit/{id}")
	public String getEdit(Model model, @RequestParam(name="error", required=false) String error,@PathVariable("id") int id, @RequestParam(name="exito", required=false) String exito) {
		model.addAttribute("publicacion", publicacionServiceImpl.findById(id));
		model.addAttribute("error", error);
		model.addAttribute("exito", exito);
		return "publicacion/create";
	}
	
	
	@PostMapping("/save")
	public String getSave(@Valid @ModelAttribute("publicacion") Publicacion publicacion,@ModelAttribute("id_publicacion") int id_publicacion,@ModelAttribute("fecha") String fecha, @ModelAttribute("file") MultipartFile file) {
		String retorno="/";
		publicacion.setFecha_publicacion(new Date());
		publicacion.setEmpresa(empresaServiceImp.findLast());
		
		if(publicacion.getId_publicacion()>0) {
			System.out.println("no es null el id");
			Publicacion pub = publicacionServiceImpl.findById(publicacion.getId_publicacion());
			publicacion.setEstado(pub.getEstado());
		}else {
			System.out.println("es null el id"+publicacion.getId_publicacion());
			publicacion.setEstado(1);
		}
		Publicacion p = publicacionServiceImpl.updatePublicacion(publicacion);
		retorno = "redirect:/publicacion/edit/"+p.getId_publicacion()+"?exito";
		String nombre_completo="";
		if(!file.isEmpty()) {
			try {
				nombre_completo = fileService.subirFile(file, 1);
				Imagen img =new Imagen(p, "/imagen/imgs/"+nombre_completo);
				Imagen imagen = imagenServiceImpl.updateImagen(img);
			}
			catch (Exception e) {					
				System.out.println("ocurrio un error al subir foto");
				retorno = "redirect:/publicacion/edit/"+p.getId_publicacion()+"?error";
				e.printStackTrace();
			}
		}
		return retorno;
	}
	
	
	@PostMapping("/delete/{id}")
	public String getDelete(Model model,@PathVariable("id") int id) {
		Publicacion publicacion = publicacionServiceImpl.findById(id);
		publicacion.setEstado(0);
		publicacionServiceImpl.updatePublicacion(publicacion);
		return "redirect:/publicacion/index";
	}
	
	@PostMapping("/getup/{id}")
	public String getActivar(Model model,@PathVariable("id") int id) {
		Publicacion publicacion = publicacionServiceImpl.findById(id);
		publicacion.setEstado(1);
		publicacionServiceImpl.updatePublicacion(publicacion);
		return "redirect:/publicacion/index";
	}

	@GetMapping("/publicaciones")
	public String publicaciones(Model model) {
		model.addAttribute("publicaciones",publicacionServiceImpl.findAllValidate());
		return "publicacion/publicaciones";
	}
	
	@GetMapping("/ver_publicacion/{id}")
	public String ver_publicacion(Model model, @PathVariable("id") int id, @RequestParam(name="error", required=false) String error, @RequestParam(name="exito", required=false) String exito) {
		model.addAttribute("publicacion", publicacionServiceImpl.findById(id));
		model.addAttribute("error", error);
		return "publicacion/ver_publicacion";
	}
	
	@PostMapping("/comentario/save")
	public String saveComentario(@ModelAttribute("id_publicacion") int id_publicacion,@ModelAttribute("comentario") String comentario) {
		String redireccion = "redirect:/publicacion/ver_publicacion/"+id_publicacion+"?error";
		System.out.println(comentario);
		
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null && !comentario.isEmpty()) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
				Users user_valid = userService.findUserByUsername(user.getUsername());
				Comentario coment = comentarioServiceImpl.update(new Comentario(publicacionServiceImpl.findById(id_publicacion), user_valid, comentario, 1, new Date()));
				if(coment!=null) {
					redireccion = "redirect:/publicacion/ver_publicacion/"+id_publicacion+"?exito";
				}
			}
		}
		return redireccion;
	}
	
}






