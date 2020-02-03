package fia.ues.edu.siam.Services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fia.ues.edu.siam.Services.PublicacionService;
import fia.ues.edu.siam.entity.Publicacion;
import fia.ues.edu.siam.repository.PublicacionRepository;

@Service("publicacionServiceImpl")
public class PublicacionServiceImpl implements PublicacionService{

	@Autowired
	@Qualifier("publicacionRepository")
	private PublicacionRepository publicacionRepository;

	@Override
	public Publicacion findById(int id) {
		return publicacionRepository.findById(id);
	}

	@Override
	public List<Publicacion> findAll() {
		return publicacionRepository.findAll();
	}

	@Override
	public Publicacion updatePublicacion(Publicacion publicacion) {
		return publicacionRepository.save(publicacion);
	}

	@Override
	public List<Publicacion> findAllValidate() {
		return publicacionRepository.findAllValidate();
	}
	
	
	
}
