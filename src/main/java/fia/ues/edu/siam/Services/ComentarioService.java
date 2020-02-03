package fia.ues.edu.siam.Services;

import java.util.List;

import fia.ues.edu.siam.entity.Animal;
import fia.ues.edu.siam.entity.Comentario;

public interface ComentarioService {
	
	public abstract Comentario findById(int id);
	public abstract Comentario update(Comentario comentario);
	public abstract List<Comentario> findByLastestValid(int id_publicacion,int limite, int limite2);
	
}
