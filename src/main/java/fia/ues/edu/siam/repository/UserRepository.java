package fia.ues.edu.siam.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fia.ues.edu.siam.entity.User;
import fia.ues.edu.siam.entity.UserRole;


@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Serializable>{
	
	public abstract User findByUsername(String username);

	@Query(value = "select * from user where user.id = ? ", nativeQuery = true)
	public User findById(int ID);
	
	@Query(value = "select count(*) from user", nativeQuery = true)
	public int count_user();
	
	@Query(value = "select count(*) from user join user_role on user.id = user_role.id and user_role.role=\'admin_role\';", nativeQuery = true)
	public int count_admin();

	@Query(value = "select * from user where user.id != ? and username != \'superuser\' ", nativeQuery = true)
	public abstract List<User> findAllNotI(int id);
	
	@Query(value = "select id, apellido,direccion,enabled, fecha_nacimiento,nombre, password, username, (select count(estado) from mensaje where mensaje.id_emisor=? and mensaje.id_receptor = user.id and (estado=0 or isNull(estado))) as cantidad from user where  user.id != ? and username != \'superuser\';", nativeQuery = true)
	public abstract List<User> findAllNotIdWithCant(int id, int id2);


	@Query(value = "select * from user where username like %?% and username != \'superuser\' and id != ?", nativeQuery = true)
	public abstract List<User> findAllUser(String username, int id);

	@Query(value = "select u.id,u.apellido, u.cantidad, u.direccion, u.enabled, u.fecha_nacimiento, u.nombre, u.password, u.username from user as u join user_role as role on u.id = role.id and role.role=\'admin_role\' limit 1;", nativeQuery = true)
	public abstract User findUserByFirstAdminRole();
	
	@Query(value = "select u.id, u.apellido,u.direccion,u.enabled, u.fecha_nacimiento,u.nombre, u.password, u.username, (select count(estado) from mensaje as m where m.id_emisor = ? and (estado=0 or isNull(estado))) as cantidad from user as u where  u.id != ? limit 1;", nativeQuery = true)
	public abstract User findUserCount(int id, int id2);
	
}
