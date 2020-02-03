package fia.ues.edu.siam.Services;

import java.util.List;

import fia.ues.edu.siam.entity.UserRole;

public interface UserRoleService {
	public abstract UserRole updateUserRole(UserRole role);
	public abstract List<UserRole> findUserRoleAdmin();
}
