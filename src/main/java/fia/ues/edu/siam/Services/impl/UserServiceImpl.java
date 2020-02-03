package fia.ues.edu.siam.Services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fia.ues.edu.siam.Services.UserService;
import fia.ues.edu.siam.entity.User;
import fia.ues.edu.siam.repository.UserRepository;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRespository;
	
	
	@Override
	public User updateUser(User user) {
		return userRespository.save(user);
	}


	@Override
	public User findUserById(int id) {
		return userRespository.findById(id);
	}


	@Override
	public User findUserByUsername(String username) {
		return userRespository.findByUsername(username);
	}


	@Override
	public int cantidad_users() {
		return userRespository.count_user();
	}


	@Override
	public int cantidad_admin() {
		return userRespository.count_admin();
	}
	
	@Override
	public List<User> findAll(int id){
		return userRespository.findAllNotI(id);
	}


	@Override
	public List<User> findAllNotI(int id) {
		return userRespository.findAllNotI(id);
	}


	@Override
	public List<User> findAll(String username, int id) {
		return userRespository.findAllUser(username, id);
	}


	@Override
	public List<User> findAllNotIdWithCant(int id) {
		return userRespository.findAllNotIdWithCant(id, id);
	}

	@Override
	public User findUserByFirstAdminRole() {
		return userRespository.findUserByFirstAdminRole();
	}


	@Override
	public User findUserCount(int id) {
		return userRespository.findUserCount(id, id);
	}	
	
	
}
