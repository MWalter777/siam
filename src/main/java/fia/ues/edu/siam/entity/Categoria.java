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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="categoria")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id_categoria;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_empresa", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Empresa empresa;
	
	@Column(name = "nombre_categoria",length=60)
	private String nombre_categoria;
	
	@Column(name="estado")
	private int estado;
	
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="fecha")
	private Date fecha;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="categoria")
	private Set<Animal> animales = new HashSet<Animal>();

	

	public Categoria(Empresa empresa, String nombre_categoria, int estado, Date fecha) {
		super();
		this.empresa = empresa;
		this.nombre_categoria = nombre_categoria;
		this.estado = estado;
		this.fecha = fecha;
	}



	public Categoria(Empresa empresa, String nombre_categoria, Date fecha, Set<Animal> animales) {
		super();
		this.empresa = empresa;
		this.nombre_categoria = nombre_categoria;
		this.fecha = fecha;
		this.animales = animales;
		this.estado=1;
	}

	
	
	public Categoria() {
		super();
	}



	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	

	public int getId_categoria() {
		return id_categoria;
	}



	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
	}



	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getNombre_categoria() {
		return nombre_categoria;
	}

	public void setNombre_categoria(String nombre_categoria) {
		this.nombre_categoria = nombre_categoria;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Set<Animal> getAnimales() {
		return animales;
	}

	public void setAnimales(Set<Animal> animales) {
		this.animales = animales;
	}
	
	public void insertAnimal(Animal animal) {
		animales.add(animal);
	}
	
	
	
	
	
}
