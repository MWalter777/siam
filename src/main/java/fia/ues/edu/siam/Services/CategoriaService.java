package fia.ues.edu.siam.Services;

import java.util.List;

import fia.ues.edu.siam.entity.Categoria;

public interface CategoriaService {
	
	public abstract Categoria findById(int id);
	public abstract List<Categoria> findAll();
	public abstract Categoria updateCategoria(Categoria categoria);

}
