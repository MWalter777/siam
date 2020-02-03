package fia.ues.edu.siam.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fia.ues.edu.siam.entity.Imagen;

@Repository("imagenRepository")
public interface ImagenRepository extends JpaRepository<Imagen, Serializable>{
	
	@Query(value = "select * from imagen where imagen.id_imagen = ? ", nativeQuery = true)
	public abstract Imagen findById(int id);
	
}
