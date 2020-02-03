package fia.ues.edu.siam.Services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fia.ues.edu.siam.Services.ImagenService;
import fia.ues.edu.siam.entity.Imagen;
import fia.ues.edu.siam.repository.ImagenRepository;

@Service("imagenServiceImpl")
public class ImagenServiceImpl implements ImagenService{
	
	@Autowired
	@Qualifier("imagenRepository")
	private ImagenRepository imagenRepository;

	@Override
	public Imagen findById(int id) {
		return imagenRepository.findById(id);
	}

	@Override
	public List<Imagen> findAll() {
		return imagenRepository.findAll();
	}

	@Override
	public Imagen updateImagen(Imagen imagen) {
		return imagenRepository.save(imagen);
	}
	
}
