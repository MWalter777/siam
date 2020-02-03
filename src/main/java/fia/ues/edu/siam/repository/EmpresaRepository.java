package fia.ues.edu.siam.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import fia.ues.edu.siam.entity.Empresa;


@Repository("empresaRepository")
public interface EmpresaRepository extends JpaRepository<Empresa, Serializable>{
	
	@Query(value = "select count(*) from empresa limit 1", nativeQuery = true)
	public int countEmpresa();
	
	@Query(value = "select * from empresa limit 1", nativeQuery = true)
	public Empresa findLast();
	
	
}
