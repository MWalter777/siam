package fia.ues.edu.siam.Services;

import java.util.List;


import fia.ues.edu.siam.entity.User;


public interface UserService {
	public abstract User updateUser(User user);
	public abstract User findUserById(int id);	
	public abstract User findUserByUsername(String username);
	public abstract int cantidad_users();
	public abstract int cantidad_admin();
	public abstract List<User> findAll(int id);
	public abstract List<User> findAllNotI(int id);
	public abstract List<User> findAll(String username, int id);
	public abstract List<User> findAllNotIdWithCant(int id);
	public abstract User findUserByFirstAdminRole();
	public abstract User findUserCount(int id);
	
}
