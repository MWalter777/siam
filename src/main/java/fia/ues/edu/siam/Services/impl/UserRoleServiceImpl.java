package fia.ues.edu.siam.Services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fia.ues.edu.siam.Services.UserRoleService;
import fia.ues.edu.siam.entity.UserRole;
import fia.ues.edu.siam.repository.RoleRepository;


@Service("userRoleServiceImpl")
public class UserRoleServiceImpl implements UserRoleService{


	@Autowired
	@Qualifier("roleRepository")
	private RoleRepository roleRespository;
	
	@Override
	public UserRole updateUserRole(UserRole role) {
		return roleRespository.save(role);
	}

	@Override
	public List<UserRole> findUserRoleAdmin() {
		return roleRespository.findRolesAdmin();
	}	

}
