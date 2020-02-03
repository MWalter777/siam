package fia.ues.edu.siam.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fia.ues.edu.siam.entity.Servicios;

@Repository("serviciosRepository")
public interface ServiciosRepository extends JpaRepository<Servicios, Serializable>{
	
	@Query(value = "select * from servicios where servicios.id_empresa = ? and servicios.estado = 1", nativeQuery = true)
	public List<Servicios> findServiciosEmpresaById(int id);
	
	@Query(value = "select * from servicios where servicios.id_empresa = ?", nativeQuery = true)
	public List<Servicios> findServiciosEmpresaAll(int id);
	
	@Query(value = "select * from servicios where servicios.id_servicios = ?", nativeQuery = true)
	public Servicios findServiciosById(int id);
	
	
}
