package fia.ues.edu.siam.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="id", unique=true, nullable = false)
	private Integer id;
	
	@Column(name = "username", unique = true, nullable= false, length = 45)
	private String username;
	
	@JsonIgnore
	@Column(name="password", nullable=false, length=60)
	private String password;
	
	@JsonIgnore
	@Column(name="enabled", nullable=false)
	private boolean enabled;
	
	@JsonIgnore
	@Column(name="nombre", nullable= true, length=60)
	private String nombre;
	
	@JsonIgnore
	@Column(name="apellido", nullable = true, length=60)
	private String apellido;
	
	@Column(name="cantidad", nullable = true)
	private int cantidad = 0;
	
	
	@JsonIgnore
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_nacimiento", nullable=true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha_nacimiento;
	
	@Column(name = "direccion", nullable=true)
	private String direccion;
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.EAGER, mappedBy="users")
	private Set<UserRole> userRole = new HashSet<UserRole>();
	
	public Users(String username, String password, boolean enabled, Set<UserRole> userRole) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.userRole = userRole;
	}

	public Users(String username, String password, boolean enabled) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}
	
	public Users(String username, String password, boolean enabled, String nombre, String apellido, Date fecha_nacimiento, String direccion, UserRole userRol) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fecha_nacimiento = fecha_nacimiento;
		this.direccion = direccion;
		this.userRole.add(userRol);
	}

	public Users() {
		super();
	}
	
	
	

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public void insertRole(UserRole role) {
		userRole.add(role);
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public Set<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}
	
	
	
	
	
	
}
