package fia.ues.edu.siam.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fia.ues.edu.siam.entity.Categoria;

@Repository("categoriaRepository")
public interface CategoriaRepository extends JpaRepository<Categoria, Serializable>{
	
	@Query(value = "select * from categoria where categoria.id_categoria = ?", nativeQuery = true)
	public Categoria findById(int id);
	
	
	
}
