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
@Table(name = "publicacion")
public class Publicacion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id_publicacion;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_empresa", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Empresa empresa;
	
	@Column(name="mensaje_publicacion")
	private String mensaje_publicacion;
	
	@Column(name="estado")
	private int estado;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="fecha_publicacion")
	private Date fecha_publicacion;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="publicacion")
	private Set<Imagen> imagenes = new HashSet<Imagen>();

	@OneToMany(fetch=FetchType.EAGER, mappedBy="publicacion")
	private Set<Comentario> comentarios = new HashSet<Comentario>();
	
	
	public Publicacion(Empresa empresa, String mensaje_publicacion, int estado, Date fecha_publicacion,
			Set<Imagen> imagenes) {
		super();
		this.empresa = empresa;
		this.mensaje_publicacion = mensaje_publicacion;
		this.estado = estado;
		this.fecha_publicacion = fecha_publicacion;
		this.imagenes = imagenes;
	}

	public Publicacion(Empresa empresa, String mensaje_publicacion, int estado, Date fecha_publicacion) {
		super();
		this.empresa = empresa;
		this.mensaje_publicacion = mensaje_publicacion;
		this.estado = estado;
		this.fecha_publicacion = fecha_publicacion;
	}

	public Publicacion(int id_publicacion, Empresa empresa, String mensaje_publicacion, int estado,
			Date fecha_publicacion, Set<Imagen> imagenes) {
		super();
		this.id_publicacion = id_publicacion;
		this.empresa = empresa;
		this.mensaje_publicacion = mensaje_publicacion;
		this.estado = estado;
		this.fecha_publicacion = fecha_publicacion;
		this.imagenes = imagenes;
	}

	public Publicacion() {
	}

	public void setImagen(Imagen imagen) {
		imagenes.add(imagen);
	}

	public Set<Imagen> getImagenes() {
		return imagenes;
	}

	public void setImagenes(Set<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

	
	
	
	
	public Set<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public int getId_publicacion() {
		return id_publicacion;
	}

	public void setId_publicacion(int id_publicacion) {
		this.id_publicacion = id_publicacion;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getMensaje_publicacion() {
		return mensaje_publicacion;
	}

	public void setMensaje_publicacion(String mensaje_publicacion) {
		this.mensaje_publicacion = mensaje_publicacion;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Date getFecha_publicacion() {
		return fecha_publicacion;
	}

	public void setFecha_publicacion(Date fecha_publicacion) {
		this.fecha_publicacion = fecha_publicacion;
	}
	
		
	
}
