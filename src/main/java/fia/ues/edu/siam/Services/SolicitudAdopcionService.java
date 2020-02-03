package fia.ues.edu.siam.Services;

import java.util.List;


import fia.ues.edu.siam.entity.SolicitudAdopcion;

public interface SolicitudAdopcionService {
	
	public abstract SolicitudAdopcion findSAdopcionById(int id_adopcion);
	
	public abstract List<SolicitudAdopcion> findAllSAdopcionByUser(int id);
	
	public abstract List<SolicitudAdopcion> findAllSAdopcionByAnimal(int id_animal);
	
	public abstract List<SolicitudAdopcion> findAllSAdopcionAcceptd(int estado);
	
	public abstract List<SolicitudAdopcion> findAll();
	
	public abstract SolicitudAdopcion updateSolicitud(SolicitudAdopcion solicitudAdopcion);
	
	public abstract SolicitudAdopcion findSAdopcionByUserLast(int id);
	
	public abstract int exists(int id_animal, int id);
	
}
