package fia.ues.edu.siam.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import fia.ues.edu.siam.entity.UserRole;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<UserRole, Serializable>{
	
	@Query(value = "select * from user_role where user_role.id_user_role = ? ", nativeQuery = true)
	public UserRole findById(int ID);
	
	@Query(value = "select * from user_role where user_role.role = \'admin_role\'", nativeQuery = true)
	public List<UserRole> findRolesAdmin();
	
}
