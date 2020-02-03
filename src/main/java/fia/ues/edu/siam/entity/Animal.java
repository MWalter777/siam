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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="animal")
public class Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="id_animal", unique=true, nullable=false)
	private Integer id_animal;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_categoria",nullable=false)
	private Categoria categoria;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="animal")
	private Set<Imagen> imagenes = new HashSet<Imagen>();
	
	@Column(name="nombre_animal",length=60,nullable=false)
	private String nombre_animal;
	
	@Column(name="raza_animal",length=60,nullable=false)
	private String raza_animal;
	
	@Column(name="descripcion_animal",length=255,nullable=false)
	private String descripcion_animal;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="fecha_rescate")
	private Date fecha_rescate;
	
	@Column(name="edad_aproximada")
	private int edad_aproximada;
	
	@Column(name="estado_animal")
	private int estado_animal;

	public Animal(Categoria categoria, String nombre_animal, String raza_animal, Date fecha_rescate,
			int edad_aproximada, int estado_animal) {
		super();
		this.categoria = categoria;
		this.nombre_animal = nombre_animal;
		this.raza_animal = raza_animal;
		this.fecha_rescate = fecha_rescate;
		this.edad_aproximada = edad_aproximada;
		this.estado_animal = estado_animal;
	}

	public Animal(Categoria categoria, Set<Imagen> imagenes, String nombre_animal, String raza_animal,
			Date fecha_rescate, int edad_aproximada, int estado_animal) {
		super();
		this.categoria = categoria;
		this.imagenes = imagenes;
		this.nombre_animal = nombre_animal;
		this.raza_animal = raza_animal;
		this.fecha_rescate = fecha_rescate;
		this.edad_aproximada = edad_aproximada;
		this.estado_animal = estado_animal;
	}

	public Animal(Integer id_animal, Categoria categoria, Set<Imagen> imagenes, String nombre_animal,
			String raza_animal, Date fecha_rescate, int edad_aproximada, int estado_animal) {
		super();
		this.id_animal = id_animal;
		this.categoria = categoria;
		this.imagenes = imagenes;
		this.nombre_animal = nombre_animal;
		this.raza_animal = raza_animal;
		this.fecha_rescate = fecha_rescate;
		this.edad_aproximada = edad_aproximada;
		this.estado_animal = estado_animal;
	}

	public Animal() {

	}
	
	

	public String getDescripcion_animal() {
		return descripcion_animal;
	}

	public void setDescripcion_animal(String descripcion_animal) {
		this.descripcion_animal = descripcion_animal;
	}

	public Integer getId_animal() {
		return id_animal;
	}

	public void setId_animal(Integer id_animal) {
		this.id_animal = id_animal;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Set<Imagen> getImagenes() {
		return imagenes;
	}

	public void setImagenes(Set<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

	public String getNombre_animal() {
		return nombre_animal;
	}

	public void setNombre_animal(String nombre_animal) {
		this.nombre_animal = nombre_animal;
	}

	public String getRaza_animal() {
		return raza_animal;
	}

	public void setRaza_animal(String raza_animal) {
		this.raza_animal = raza_animal;
	}

	public Date getFecha_rescate() {
		return fecha_rescate;
	}

	public void setFecha_rescate(Date fecha_rescate) {
		this.fecha_rescate = fecha_rescate;
	}

	public int getEdad_aproximada() {
		return edad_aproximada;
	}

	public void setEdad_aproximada(int edad_aproximada) {
		this.edad_aproximada = edad_aproximada;
	}

	public int getEstado_animal() {
		return estado_animal;
	}

	public void setEstado_animal(int estado_animal) {
		this.estado_animal = estado_animal;
	}
	
	public void insertImagen(Imagen imagen) {
		imagenes.add(imagen);
	}
	
	
	
	
	
	
	
	
	
}
