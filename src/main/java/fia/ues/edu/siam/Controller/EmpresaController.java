package fia.ues.edu.siam.Controller;

import java.io.File;
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

import fia.ues.edu.siam.Services.impl.EmpresaServiceImp;
import fia.ues.edu.siam.Services.impl.FileServiceImpl;
import fia.ues.edu.siam.Services.impl.ServicioServiceImpl;
import fia.ues.edu.siam.entity.Empresa;
import fia.ues.edu.siam.entity.Servicios;
import fia.ues.edu.siam.entity.Users;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

	@Autowired
	@Qualifier("fileServiceImpl")
	private FileServiceImpl fileService;
	
	@Autowired
	@Qualifier("empresaServiceImpl")
	private EmpresaServiceImp empresaService;
	
	@Autowired
	@Qualifier("servicioServiceImpl")
	private ServicioServiceImpl servicioServiceImpl;
	
	@GetMapping("/editar")
	public String getEmpresa(Model model, @RequestParam(name="error", required=false) String error, @RequestParam(name="exito", required=false) String exito) {
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("super_user"))) {
					Empresa empresa = empresaService.findLast();
					model.addAttribute("error", error);
					model.addAttribute("exito", exito);
					model.addAttribute("empresa", empresa);
					model.addAttribute("direccion","/imgs/"+ empresa.getImagen_empresa());
					return "empresa/editar";
				}
			}
		}
		return "redirect:/";
	}
	
	@PostMapping("/update")
	public String getUpdate(@Valid @ModelAttribute("empresa") Empresa empresa,@ModelAttribute("file") MultipartFile file,@ModelAttribute("fecha") String fecha ) {
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		Date fecha_nacimiento = null;
		Empresa emp = empresaService.findLast();
		try {
			fecha_nacimiento = formateador.parse(fecha);
		} catch (ParseException e) {
			return "redirect:/empresa/editar?error";
		}
		String nombre_completo ="";

		if(!file.isEmpty()) {
			try {
				nombre_completo = fileService.subirFile(file, 1);
			}
			catch (Exception e) {
				
				System.out.println("ocurrio un error al subir foto");
				/*
				 * Seguramente si ocurrio un error es por que no esta el directorio, asi que creemoslo
				 */
				String nombre_sio = System.getProperty("os.name");
				if (nombre_sio.toUpperCase().contains("WINDOWS")) {
					System.out.println("es: "+nombre_sio);
					//solo lo hare para windows, pero es lo mismo para linux solo cambia el directorio
					File directorio = new File("/siam_imgs");
					if (!directorio.exists()) {
			            if (directorio.mkdirs()) {
			                System.out.println("Directorio creado");
			            } else {
			                System.out.println("Error al crear directorio");
			            }
			        }
					
				}else {
					System.out.println("Seguramente es linux");
				}	
				e.printStackTrace();
				return "redirect:/empresa/editar?error";
			}
		}else {
			nombre_completo = emp.getImagen_empresa();
		}
		empresa.setImagen_empresa(nombre_completo);
		empresa.setFecha_creacion(fecha_nacimiento);
		System.out.println("NIT:"+empresa.getNit_empresa() + " Codigo:"+empresa.getCodigo_empresa() + " Descripcion:"+empresa.getDescripcion() +" ID: "+ empresa.getId_empresa() + " imagen: "+empresa.getImagen_empresa() + " Razon social: "+empresa.getRazon_social() +" Fecha: "+empresa.getFecha_creacion()+file.getOriginalFilename());
		empresaService.updateEmpresa(empresa);
		return "redirect:/empresa/editar?exito";
	}
	
	@GetMapping("/descripcion")
	public String getDescripcion(Model model) {
		Empresa empresa = empresaService.findLast();
		List<Servicios> servicios = servicioServiceImpl.findAllByEmpresa(empresa.getId_empresa());
		model.addAttribute("servicios", servicios);
		model.addAttribute("direccion","/imagen/imgs/"+ empresa.getImagen_empresa());
		model.addAttribute("empresa",empresa);
		return "empresa/descripcion";
	}
	
	@GetMapping("/servicios")
	public String getService(Model model) {
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				Empresa empresa = empresaService.findLast();
				List<Servicios> servicios = servicioServiceImpl.findAllAll(empresa.getId_empresa());
				model.addAttribute("servicios", servicios);
				if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin_role"))) {
					return "empresa/bajar_servicio";
				}
				if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("super_user"))) {
					return "empresa/bajar_servicio_super";
				}
			}
		}
		return "redirect:/";
	}
	

	@PostMapping("/bajar/{id}")
	public String getServicioEliminar(Model model,@PathVariable("id") int id, @RequestParam(name="error", required=false) String error) {
		Servicios service = servicioServiceImpl.findServiceById(id);
		service.setEstado(0);
		servicioServiceImpl.updateService(service);
		return "redirect:/empresa/servicios";
	}
	

	@PostMapping("/subir/{id}")
	public String getServicioSubir(Model model,@PathVariable("id") int id, @RequestParam(name="error", required=false) String error) {
		Servicios service = servicioServiceImpl.findServiceById(id);
		service.setEstado(1);
		servicioServiceImpl.updateService(service);
		return "redirect:/empresa/servicios";
	}
	
	@PostMapping("/servicio_save")
	public String saveService(@ModelAttribute("nombre_servicio") String nombre_servicio, @ModelAttribute("descripcion_servicio") String descripcion_servicio) {
		Empresa empresa = empresaService.findLast();
		servicioServiceImpl.updateService(new Servicios(empresa, nombre_servicio, descripcion_servicio, 1));
		return "redirect:/empresa/servicios";
	}
	
	
}
