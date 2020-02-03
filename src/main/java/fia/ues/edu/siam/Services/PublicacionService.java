package fia.ues.edu.siam.Services;

import java.util.List;

import fia.ues.edu.siam.entity.Publicacion;

public interface PublicacionService {
	
	public abstract Publicacion findById(int id);
	public abstract List<Publicacion> findAll();
	public abstract List<Publicacion> findAllValidate();
	public abstract Publicacion updatePublicacion(Publicacion publicacion);
	
}
