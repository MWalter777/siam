package fia.ues.edu.siam.Services;

import java.util.List;


import fia.ues.edu.siam.entity.Servicios;

public interface ServicioService {

	public abstract List<Servicios> findAllByEmpresa(int id);
	public abstract List<Servicios> findAllAll(int id);
	public abstract Servicios updateService(Servicios servicio);
	public abstract Servicios findServiceById(int id);

}
