package fia.ues.edu.siam.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fia.ues.edu.siam.Services.impl.ComentarioServiceImpl;
import fia.ues.edu.siam.entity.Comentario;


@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/comentarioRest")
public class ComentarioRest {
	
	@Autowired
	@Qualifier("comentarioServiceImpl")
	private ComentarioServiceImpl comentarioServiceImpl;
	
	
	
	@GetMapping("/all/{id_publicacion}/{limite_inferior}/{limite_superior}")
	public List<Comentario> findAll_messages(Model model,@PathVariable("id_publicacion") int id_publicacion ,@PathVariable("limite_inferior") int limite_inferior, @PathVariable("limite_superior") int limite_superior){
		return comentarioServiceImpl.findByLastestValid(id_publicacion,limite_inferior, limite_superior);
	}


}
