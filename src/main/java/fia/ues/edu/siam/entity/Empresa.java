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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="empresa")
public class Empresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id_empresa;
	
	@Column(name="razon_social",nullable=false, length=60)
	private String razon_social;
	
	@Column(name = "codigo_empresa", nullable = false, length = 50)
	private String codigo_empresa;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="fecha_creacion",nullable=false)
	private Date fecha_creacion;
	
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "imagen_empresa")
	private String imagen_empresa;

	@Column(name = "nit_empresa", length=15)
	private String nit_empresa;

	@Column(name = "correo", length=60)
	private String correo;

	@Column(name = "telefono", length=15)
	private String telefono;

	

	public Empresa(String razon_social, String codigo_empresa, Date fecha_creacion, String descripcion,
			String imagen_empresa, String nit_empresa, String correo, String telefono) {
		super();
		this.razon_social = razon_social;
		this.codigo_empresa = codigo_empresa;
		this.fecha_creacion = fecha_creacion;
		this.descripcion = descripcion;
		this.imagen_empresa = imagen_empresa;
		this.nit_empresa = nit_empresa;
		this.correo = correo;
		this.telefono = telefono;
	}
	
	

	public Empresa() {
		super();
	}

	public Empresa(int id_empresa, String razon_social, String codigo_empresa, Date fecha_creacion, String descripcion,
			String imagen_empresa, String nit_empresa) {
		super();
		this.id_empresa = id_empresa;
		this.razon_social = razon_social;
		this.codigo_empresa = codigo_empresa;
		this.fecha_creacion = fecha_creacion;
		this.descripcion = descripcion;
		this.imagen_empresa = imagen_empresa;
		this.nit_empresa = nit_empresa;
	}

	public Empresa(String razon_social, String codigo_empresa, Date fecha_creacion, String descripcion,
			String imagen_empresa, String nit_empresa) {
		super();
		this.razon_social = razon_social;
		this.codigo_empresa = codigo_empresa;
		this.fecha_creacion = fecha_creacion;
		this.descripcion = descripcion;
		this.imagen_empresa = imagen_empresa;
		this.nit_empresa = nit_empresa;
	}
	
	
	
	
	
	public String getCorreo() {
		return correo;
	}



	public void setCorreo(String correo) {
		this.correo = correo;
	}



	public String getTelefono() {
		return telefono;
	}



	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	public int getId_empresa() {
		return id_empresa;
	}

	public void setId_empresa(int id_empresa) {
		this.id_empresa = id_empresa;
	}

	public String getRazon_social() {
		return razon_social;
	}

	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}

	public String getCodigo_empresa() {
		return codigo_empresa;
	}

	public void setCodigo_empresa(String codigo_empresa) {
		this.codigo_empresa = codigo_empresa;
	}

	public Date getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImagen_empresa() {
		return imagen_empresa;
	}

	public void setImagen_empresa(String imagen_empresa) {
		this.imagen_empresa = imagen_empresa;
	}

	public String getNit_empresa() {
		return nit_empresa;
	}

	public void setNit_empresa(String nit_empresa) {
		this.nit_empresa = nit_empresa;
	}
	
	
	
	
	
	
	
}
