package fia.ues.edu.siam.repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fia.ues.edu.siam.entity.Mensaje;

@Repository("mensajeRepository")
public interface MensajeRepository extends JpaRepository<Mensaje, Serializable>{
	
	@Query(value = "select * from (select * from mensaje where id_receptor = ? and id_emisor=? or id_receptor = ? and id_emisor = ? order by id_mensajes desc limit 100) as mensaje order by id_mensajes asc", nativeQuery = true)
	public List<Mensaje> findAllMyMessages(int user_to, int user_from, int user_to2, int user_from2);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update mensaje set estado=true where (id_emisor= ? and id_receptor = ?) and (estado=false);", nativeQuery = true)
	public void visto(int user_from,int user_to);	
	
	
}
















