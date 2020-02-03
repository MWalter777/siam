package fia.ues.edu.siam.Services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fia.ues.edu.siam.Services.ComentarioService;
import fia.ues.edu.siam.entity.Animal;
import fia.ues.edu.siam.entity.Comentario;
import fia.ues.edu.siam.repository.ComentarioRepository;

@Service("comentarioServiceImpl")
public class ComentarioServiceImpl implements ComentarioService{
	
	@Autowired
	@Qualifier("comentarioRepository")
	private ComentarioRepository comentarioRepository;

	@Override
	public Comentario findById(int id) {
		return comentarioRepository.findById(id);
	}

	@Override
	public Comentario update(Comentario comentario) {
		return comentarioRepository.save(comentario);
	}

	@Override
	public List<Comentario> findByLastestValid(int id_publicacion,int limite, int limite2) {
		return comentarioRepository.findByLastestValid(id_publicacion,limite, limite2);
	}

}
