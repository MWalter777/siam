package fia.ues.edu.siam.Services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fia.ues.edu.siam.Services.SolicitudAdopcionService;
import fia.ues.edu.siam.entity.SolicitudAdopcion;
import fia.ues.edu.siam.repository.AdopcionRepository;

@Service("solicitudAdopcionServiceImpl")
public class SolicitudAdopcionServiceImpl implements SolicitudAdopcionService{
	
	@Autowired
	@Qualifier("adopcionRepository")
	private AdopcionRepository adopcionRepository;

	@Override
	public SolicitudAdopcion findSAdopcionById(int id_adopcion) {
		return adopcionRepository.findSAdopcionById(id_adopcion);
	}
	
	@Override
	public int exists(int id_animal, int id) {
		return adopcionRepository.exists(id_animal, id);
	}
	

	@Override
	public List<SolicitudAdopcion> findAll() {
		return adopcionRepository.findAll();
	}
	
	
	@Override
	public SolicitudAdopcion findSAdopcionByUserLast(int id) {
		return adopcionRepository.findSAdopcionByUserLast(id);
	}



	@Override
	public List<SolicitudAdopcion> findAllSAdopcionByUser(int id) {
		return adopcionRepository.findAllSAdopcionByUser(id);
	}

	@Override
	public List<SolicitudAdopcion> findAllSAdopcionByAnimal(int id_animal) {
		return adopcionRepository.findAllSAdopcionByAnimal(id_animal);
	}

	@Override
	public List<SolicitudAdopcion> findAllSAdopcionAcceptd(int estado) {
		return adopcionRepository.findAllSAdopcionAcceptd(estado);
	}

	@Override
	public SolicitudAdopcion updateSolicitud(SolicitudAdopcion solicitudAdopcion) {
		return adopcionRepository.save(solicitudAdopcion);
	}
	
	
	
	

}
