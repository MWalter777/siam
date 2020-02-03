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
@Table(name="servicios")
public class Servicios {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id_servicios;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_empresa", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Empresa empresa;

	@Column(name="nombre_servicio", length=60)
	private String nombre_servicio;

	@Column(name="descripcion_servicio")
	private String descripcion_servicio;
	
	@Column(name="estado")
	private int estado;

	
	public Servicios() {
		super();
	}
	
	

	public Servicios(Empresa empresa, String nombre_servicio, String descripcion_servicio, int estado) {
		super();
		this.empresa = empresa;
		this.nombre_servicio = nombre_servicio;
		this.descripcion_servicio = descripcion_servicio;
		this.estado = estado;
	}



	public String getDescripcion_servicio() {
		return descripcion_servicio;
	}



	public void setDescripcion_servicio(String descripcion_servicio) {
		this.descripcion_servicio = descripcion_servicio;
	}



	public int getId_servicios() {
		return id_servicios;
	}

	public void setId_servicios(int id_servicios) {
		this.id_servicios = id_servicios;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getNombre_servicio() {
		return nombre_servicio;
	}

	public void setNombre_servicio(String nombre_servicio) {
		this.nombre_servicio = nombre_servicio;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	
	
	
	
	
	
}
