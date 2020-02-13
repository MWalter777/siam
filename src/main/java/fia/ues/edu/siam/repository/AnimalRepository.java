package fia.ues.edu.siam.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fia.ues.edu.siam.entity.Animal;


@Repository("animalRepository")
public interface AnimalRepository extends JpaRepository<Animal, Serializable>{
	
	@Query(value = "select * from animal where animal.id_animal = ? ", nativeQuery = true)
	public Animal findById(int id);
	
	
	@Query(value = "select id_animal, edad_aproximada, estado_animal, fecha_rescate, nombre_animal, raza_animal, animal.id_categoria, descripcion_animal from animal join categoria on categoria.id_categoria = animal.id_categoria and categoria.estado = 1 and animal.estado_animal = ?;", nativeQuery = true)
	public List<Animal> findByValid(int valor);
	
	
	@Query(value = "select id_animal, edad_aproximada, estado_animal, fecha_rescate, nombre_animal, raza_animal, animal.id_categoria, descripcion_animal from animal join categoria on categoria.id_categoria = animal.id_categoria and categoria.estado = 1 and animal.estado_animal = 1 order by animal.id_animal limit ?;", nativeQuery = true)
	public List<Animal> findByLastestValid(int limite);

	@Query(value = "select id_animal, edad_aproximada, estado_animal, fecha_rescate, nombre_animal, raza_animal, animal.id_categoria, descripcion_animal from animal join categoria on categoria.id_categoria = animal.id_categoria and categoria.estado = 1 and animal.estado_animal = 1 order by animal.id_animal desc offset ? limit ?;", nativeQuery = true)
	public List<Animal> findByLastestValid(int limite, int limite2);

	
	@Query(value = "select id_animal, edad_aproximada, estado_animal, fecha_rescate, nombre_animal, raza_animal, animal.id_categoria, descripcion_animal from animal join categoria on categoria.id_categoria = animal.id_categoria and categoria.estado = 1 and animal.estado_animal = 1 where animal.nombre_animal like CONCAT('%',:buscar,'%') or animal.raza_animal like CONCAT('%',:buscar,'%');", nativeQuery = true)
	public List<Animal> findAll(String buscar);
	

}
