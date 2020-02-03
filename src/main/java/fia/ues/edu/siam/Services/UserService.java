package fia.ues.edu.siam.Services;

import java.util.List;


import fia.ues.edu.siam.entity.Users;


public interface UserService {
	public abstract Users updateUser(Users user);
	public abstract Users findUserById(int id);	
	public abstract Users findUserByUsername(String username);
	public abstract int cantidad_users();
	public abstract int cantidad_admin();
	public abstract List<Users> findAll(int id);
	public abstract List<Users> findAllNotI(int id);
	public abstract List<Users> findAll(String username, int id);
	public abstract List<Users> findAllNotIdWithCant(int id);
	public abstract Users findUserByFirstAdminRole();
	public abstract Users findUserCount(int id);
	
}
