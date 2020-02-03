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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fia.ues.edu.siam.Services.impl.AnimalServiceImpl;
import fia.ues.edu.siam.Services.impl.CategoriaServiceImpl;
import fia.ues.edu.siam.Services.impl.FileServiceImpl;
import fia.ues.edu.siam.Services.impl.ImagenServiceImpl;
import fia.ues.edu.siam.entity.Animal;
import fia.ues.edu.siam.entity.Categoria;
import fia.ues.edu.siam.entity.Imagen;
import fia.ues.edu.siam.entity.User;

@Controller
@RequestMapping("/animal")
public class AnimalController {
	
	@Autowired
	@Qualifier("animalServiceImpl")
	private AnimalServiceImpl animalServiceImpl;
	
	@Autowired
	@Qualifier("categoriaServiceImpl")
	private CategoriaServiceImpl categoriaServiceImpl;
	
	@Autowired
	@Qualifier("imagenServiceImpl")
	private ImagenServiceImpl imagenServiceImpl;
	
	@Autowired
	@Qualifier("fileServiceImpl")
	private FileServiceImpl fileService;

	@GetMapping("/index")
	public String index(Model model) {
		String retornar = "redirect:/";
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin_role"))) {
					retornar = "animal/index";
				}
			}
		}else {
			retornar = "redirect:/login";
		}
		
		List<Animal> animales = animalServiceImpl.findAll();
		model.addAttribute("animales", animales);
		return retornar;
	}
	

	@GetMapping("/insert/{id}")
	public String getEliminar(Model model,@PathVariable("id") int id, @RequestParam(name="error", required=false) String error,@RequestParam(name="exito", required=false) String exito) {
		Date fecha = new Date();
		int anio = fecha.getYear() - 17 ;
		fecha.setYear(anio);
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		String fecha_actual = formateador.format(fecha);;
		model.addAttribute("id_categoria", id);
		model.addAttribute("animal", new Animal());
		model.addAttribute("error", error);
		model.addAttribute("exito", exito);
		model.addAttribute("fecha_acutal", fecha_actual);
		System.out.println(""+id);
		return "animal/insert";
	}
	
	@GetMapping("/edit/{id}")
	public String getEdit(Model model,@PathVariable("id") int id, @RequestParam(name="error", required=false) String error,@RequestParam(name="error_actualizar", required = false) String error_actualizar,@RequestParam(name="exito", required=false) String exito) {
		Date fecha = new Date();
		int anio = fecha.getYear() - 17 ;
		fecha.setYear(anio);
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		String fecha_actual = formateador.format(fecha);;
		Animal animal = animalServiceImpl.findById(id);
		model.addAttribute("id_categoria", animal.getCategoria().getId_categoria());
		model.addAttribute("animal", animal);
		model.addAttribute("fecha_acutal", fecha_actual);
		model.addAttribute("exito", exito);
		model.addAttribute("error", error);
		model.addAttribute("error_actualizar", error_actualizar);
		model.addAttribute("hay_imagenes",true);
		System.out.println(""+id);
		return "animal/insert";
	}
	
	@GetMapping("/ver/{id}")
	public String getVer(Model model,@PathVariable("id") int id, @RequestParam(name="error", required=false) String error,@RequestParam(name="exito", required=false) String exito) {
		return "redirect:/";
	}
	
	@PostMapping("/save")
	public String save(Model model,@Valid @ModelAttribute("animal") Animal animal,@ModelAttribute("id_categoria") int id_categoria,@ModelAttribute("fecha") String fecha, @ModelAttribute("file") MultipartFile file) {
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		Date fecha_rescate = null;
		try {
			fecha_rescate = formateador.parse(fecha);
		} catch (ParseException e) {
			return "redirect:/";
		}
		if(animal.getNombre_animal().isEmpty() || animal.getDescripcion_animal().isEmpty()) {
			if(animal.getId_animal() !=null && animal.getId_animal()>0)
				return "redirect:/animal/edit/"+animal.getId_animal()+"?error_actualizar=error";
			return "redirect:/animal/insert/"+id_categoria+"?error=error";
		}
		String retornar ="";
		animal.setFecha_rescate(fecha_rescate);
		animal.setCategoria(categoriaServiceImpl.findById(id_categoria));
				
		if(animal.getId_animal() !=null && animal.getId_animal()>0) {
			System.out.println("no es null el id");
			Animal an = animalServiceImpl.findById(animal.getId_animal());
			animal.setEstado_animal(an.getEstado_animal());
			retornar = "redirect:/animal/edit/"+an.getId_animal()+"?exito=insert";
		}else {
			System.out.println("es null el id"+animal.getCategoria().getNombre_categoria());
			animal.setEstado_animal(1);
			retornar = "redirect:/animal/insert/"+id_categoria+"?exito=insert";
		}
		Animal ani = animalServiceImpl.updateAnimal(animal);
		String nombre_completo="";
		if(!file.isEmpty()) {
			try {
				nombre_completo = fileService.subirFile(file, 1);
				Imagen img =new Imagen(ani, "/imagen/imgs/"+nombre_completo);
				Imagen imagen = imagenServiceImpl.updateImagen(img);
				//ani.insertImagen(imagen);
				//animalServiceImpl.updateAnimal(ani);
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
					File directorio = new File("C:\\siam_imgs");
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
				retornar = "redirect:/animal/insert/"+id_categoria+"?error";
			}
		}
		return retornar;
	}
	
	@PostMapping("/delete/{id}")
	public String getDelete(Model model,@PathVariable("id") int id, @RequestParam(name="error", required=false) String error) {
		Animal animal = animalServiceImpl.findById(id);
		animal.setEstado_animal(0);
		animalServiceImpl.updateAnimal(animal);
		return "redirect:/animal/index";
	}
	
	@PostMapping("/getup/{id}")
	public String getActivar(Model model,@PathVariable("id") int id, @RequestParam(name="error", required=false) String error) {
		Animal animal = animalServiceImpl.findById(id);
		animal.setEstado_animal(1);
		animalServiceImpl.updateAnimal(animal);
		return "redirect:/animal/index";
	}
	
	
	

}
