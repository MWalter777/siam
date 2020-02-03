package fia.ues.edu.siam.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fia.ues.edu.siam.Services.impl.CategoriaServiceImpl;
import fia.ues.edu.siam.Services.impl.EmpresaServiceImp;
import fia.ues.edu.siam.entity.Categoria;
import fia.ues.edu.siam.entity.Servicios;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {	

	@Autowired
	@Qualifier("categoriaServiceImpl")
	private CategoriaServiceImpl categoriaServiceImpl;
	
	@Autowired
	@Qualifier("empresaServiceImpl")
	private EmpresaServiceImp empresaServiceImp;
	
	
	/* 
	 * Est funcion deberia ser suficiente para validar que un usuario esta ingresando a la pagina correcta, pero no funciona
	 * por algun motivo, no tengo internet para verificar por que no funciona, asi que lo hare a pie para mientras
	 * @PreAuthorize("hasRole('admin_role')")
	 * 
	 * 
	 * En caso que no funcione me vere obligado a hacerlo de cero
	 * 
	 * usando ese metodo
	 * String retornar = "redirect:/";
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin_role"))) {
					retornar = "categoria/index";
				}
			}
		}
	 * 
	 * 
	 */
	
	
	@GetMapping("/index")
	public String index(Model model) {
		String retornar = "redirect:/";
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin_role"))) {
					retornar = "categoria/index";
				}
			}
		}else {
			retornar = "redirect:/login";
		}
		
		List<Categoria> categorias = categoriaServiceImpl.findAll();
		model.addAttribute("categorias", categorias);
		return retornar;
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute("nombre_categoria") String nombre_categoria) {
		if(!nombre_categoria.isEmpty()) {
			Categoria categoria = new Categoria(empresaServiceImp.findLast(), nombre_categoria, 1, new Date());
			categoriaServiceImpl.updateCategoria(categoria);
		}
		return "redirect:/categoria/index";
	}
	
	@PostMapping("/eliminar/{id}")
	public String getEliminar(Model model,@PathVariable("id") int id, @RequestParam(name="error", required=false) String error) {
		Categoria cat = categoriaServiceImpl.findById(id);
		cat.setEstado(0);
		categoriaServiceImpl.updateCategoria(cat);
		return "redirect:/categoria/index";
	}
	
	@PostMapping("/activar/{id}")
	public String getActivar(Model model,@PathVariable("id") int id, @RequestParam(name="error", required=false) String error) {
		Categoria cat = categoriaServiceImpl.findById(id);
		cat.setEstado(1);
		categoriaServiceImpl.updateCategoria(cat);
		return "redirect:/categoria/index";
	}
	
	
}
