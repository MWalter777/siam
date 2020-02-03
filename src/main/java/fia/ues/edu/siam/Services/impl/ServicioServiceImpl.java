package fia.ues.edu.siam.Services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fia.ues.edu.siam.Services.ServicioService;
import fia.ues.edu.siam.entity.Servicios;
import fia.ues.edu.siam.repository.ServiciosRepository;


@Service("servicioServiceImpl")
public class ServicioServiceImpl implements ServicioService{
	
	@Autowired
	@Qualifier("serviciosRepository")
	private ServiciosRepository serviciosRepository;

	@Override
	public List<Servicios> findAllByEmpresa(int id) {
		return serviciosRepository.findServiciosEmpresaById(id);
	}

	@Override
	public Servicios updateService(Servicios servicio) {
		return serviciosRepository.save(servicio);
	}

	@Override
	public Servicios findServiceById(int id) {
		return serviciosRepository.findServiciosById(id);
	}

	@Override
	public List<Servicios> findAllAll(int id) {
		return serviciosRepository.findServiciosEmpresaAll(id);
	}
	
	

}
