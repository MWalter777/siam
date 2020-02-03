package fia.ues.edu.siam.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fia.ues.edu.siam.entity.Comentario;

@Repository("comentarioRepository")
public interface ComentarioRepository extends JpaRepository<Comentario, Serializable>{
	
	@Query(value = "select * from comentario where comentario.id_comentario = ? ", nativeQuery = true)
	public Comentario findById(int id);
	
	
	@Query(value = "select * from comentario where estado = 1 and id_publicacion = ?  order by id_comentario desc limit ?, ?;", nativeQuery = true)
	public List<Comentario> findByLastestValid(int id_publicacion,int limite, int limite2);

}









