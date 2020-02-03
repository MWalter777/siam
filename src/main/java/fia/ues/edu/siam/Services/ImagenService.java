package fia.ues.edu.siam.Services;

import java.util.List;

import fia.ues.edu.siam.entity.Imagen;

public interface ImagenService {
	
	public abstract Imagen findById(int id);
	public abstract List<Imagen> findAll();
	public abstract Imagen updateImagen(Imagen imagen);
	

}
