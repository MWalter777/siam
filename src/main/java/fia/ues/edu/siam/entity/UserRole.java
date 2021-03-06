package fia.ues.edu.siam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "UserRole", uniqueConstraints = @UniqueConstraint(columnNames= {"role","id"}))
public class UserRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="user_role_id", unique=true, nullable=false)
	private Integer userRoleId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id", nullable=false)
	private Users users;

	@Column(name="role", nullable=false, length=11)
	private String role;
	
	public UserRole(Users user, String role) {
		super();
		this.users = user;
		this.role = role;
	}

	public UserRole() {
		super();
	}

	public Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
	

	public Users getUser() {
		return users;
	}

	public void setUser(Users user) {
		this.users = user;
	}


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
	
}
