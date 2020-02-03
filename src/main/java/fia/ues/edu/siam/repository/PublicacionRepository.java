package fia.ues.edu.siam.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fia.ues.edu.siam.entity.Publicacion;

@Repository("publicacionRepository")
public interface PublicacionRepository extends JpaRepository<Publicacion, Serializable>{
	
	@Query(value = "select * from publicacion where publicacion.id_publicacion = ? ", nativeQuery = true)
	public abstract Publicacion findById(int id);
	
	@Query(value = "select * from publicacion where publicacion.estado=1 order by id_publicacion desc", nativeQuery = true)
	public abstract List<Publicacion> findAllValidate();
	
	
}
