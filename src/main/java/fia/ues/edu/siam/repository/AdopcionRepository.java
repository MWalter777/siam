package fia.ues.edu.siam.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fia.ues.edu.siam.entity.SolicitudAdopcion;

@Repository("adopcionRepository")
public interface AdopcionRepository extends JpaRepository<SolicitudAdopcion, Serializable>{
	
	@Query(value = "select * from solicitud_adopcion where solicitud_adopcion.id_adopcion = ? ", nativeQuery = true)
	public abstract SolicitudAdopcion findSAdopcionById(int id_adopcion);
	
	@Query(value = "select * from solicitud_adopcion where solicitud_adopcion.id = ? ", nativeQuery = true)
	public abstract List<SolicitudAdopcion> findAllSAdopcionByUser(int id);
	
	@Query(value = "select * from solicitud_adopcion where solicitud_adopcion.id = ? limit 1", nativeQuery = true)
	public abstract SolicitudAdopcion findSAdopcionByUserLast(int id);
	
	@Query(value = "select * from solicitud_adopcion where solicitud_adopcion.id_animal = ? ", nativeQuery = true)
	public abstract List<SolicitudAdopcion> findAllSAdopcionByAnimal(int id_animal);
	
	@Query(value = "select * from solicitud_adopcion where solicitud_adopcion.estado = ? ", nativeQuery = true)
	public abstract List<SolicitudAdopcion> findAllSAdopcionAcceptd(int estado);
	
	@Query(value = "select count(*) from solicitud_adopcion where solicitud_adopcion.id_animal = ? and solicitud_adopcion.id= ?", nativeQuery = true)
	public abstract int exists(int id_animal, int id);
	
	
}
