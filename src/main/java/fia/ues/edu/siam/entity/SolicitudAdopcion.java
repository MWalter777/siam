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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="solicitud_adopcion")
public class SolicitudAdopcion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="id_adopcion", unique=true, nullable=false)
	private Integer id_adopcion;
	
	@Column(name="codigo_solicitud",length=10, nullable=false)
	private String codigo_solicitud;
	
	@Column(name="descripcion_solicitud", nullable=false)
	private String descripcion_solicitud;
	
	@Column(name="numero_mascotas", nullable=false)
	private int numero_mascotas;
	
	@Column(name="salario_actual", nullable=false)
	private double salario_actual;
	
	
	@Column(name="estado")
	private int estado;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_animal")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Animal animal;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	

	public SolicitudAdopcion() {
		super();
	}

	public SolicitudAdopcion(Integer id_adopcion, String codigo_solicitud, String descripcion_solicitud,
			int numero_mascotas, double salario_actual, int estado, Animal animal, User user) {
		super();
		this.id_adopcion = id_adopcion;
		this.codigo_solicitud = codigo_solicitud;
		this.descripcion_solicitud = descripcion_solicitud;
		this.numero_mascotas = numero_mascotas;
		this.salario_actual = salario_actual;
		this.estado = estado;
		this.animal = animal;
		this.user = user;
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId_adopcion() {
		return id_adopcion;
	}

	public void setId_adopcion(Integer id_adopcion) {
		this.id_adopcion = id_adopcion;
	}

	public String getCodigo_solicitud() {
		return codigo_solicitud;
	}

	public void setCodigo_solicitud(String codigo_solicitud) {
		this.codigo_solicitud = codigo_solicitud;
	}

	public String getDescripcion_solicitud() {
		return descripcion_solicitud;
	}

	public void setDescripcion_solicitud(String descripcion_solicitud) {
		this.descripcion_solicitud = descripcion_solicitud;
	}

	public int getNumero_mascotas() {
		return numero_mascotas;
	}

	public void setNumero_mascotas(int numero_mascotas) {
		this.numero_mascotas = numero_mascotas;
	}

	public double getSalario_actual() {
		return salario_actual;
	}

	public void setSalario_actual(double salario_actual) {
		this.salario_actual = salario_actual;
	}


	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
	
	
	
	
	
}
