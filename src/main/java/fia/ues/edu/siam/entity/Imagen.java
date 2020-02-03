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

@Entity
@Table(name="imagen")
public class Imagen {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="id_imagen", unique=true, nullable=false)
	private Integer id_imagen;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_publicacion",nullable=true)
	private Publicacion publicacion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_animal",nullable=true)
	private Animal animal;
	
	@Column(name="direccion_imagen")
	private String direccion_imagen;

	
	
	public Imagen(Publicacion publicacion, String direccion_imagen) {
		super();
		this.publicacion = publicacion;
		this.direccion_imagen = direccion_imagen;
	}



	public Imagen(Animal animal, String direccion_imagen) {
		super();
		this.animal = animal;
		this.direccion_imagen = direccion_imagen;
	}



	public Imagen() {
		super();
	}




	public Animal getAnimal() {
		return animal;
	}



	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
	
	
	

	public Integer getId_imagen() {
		return id_imagen;
	}



	public void setId_imagen(Integer id_imagen) {
		this.id_imagen = id_imagen;
	}



	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}

	public String getDireccion_imagen() {
		return direccion_imagen;
	}

	public void setDireccion_imagen(String direccion_imagen) {
		this.direccion_imagen = direccion_imagen;
	}
	
	

}
