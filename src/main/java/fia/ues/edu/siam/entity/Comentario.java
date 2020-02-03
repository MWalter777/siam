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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="comentario")
public class Comentario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="id_comentario", unique=true, nullable=false)
	private Integer id_comentario;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_publicacion",nullable=false)
	private Publicacion publicacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_usuario",nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users usuario;
	
	@Column(name = "mensaje_comentario",length=510)
	private String mensaje_comentario;
	
	@Column(name="estado")
	private int estado;
	
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="fecha")
	private Date fecha;

	public Comentario(Publicacion publicacion, Users usuario, String mensaje_comentario, int estado, Date fecha) {
		super();
		this.publicacion = publicacion;
		this.usuario = usuario;
		this.mensaje_comentario = mensaje_comentario;
		this.estado = estado;
		this.fecha = fecha;
	}
	
	


	public Comentario() {
		super();
	}




	public Integer getId_comentario() {
		return id_comentario;
	}


	public void setId_comentario(Integer id_comentario) {
		this.id_comentario = id_comentario;
	}


	@JsonIgnore
	public Publicacion getPublicacion() {
		return publicacion;
	}


	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}


	@JsonIgnore
	public Users getUsuario() {
		return usuario;
	}
	
	@JsonSerialize
	public String username_emisor() {
		return usuario.getUsername();
	}


	public void setUsuario(Users usuario) {
		this.usuario = usuario;
	}


	public String getMensaje_comentario() {
		return mensaje_comentario;
	}


	public void setMensaje_comentario(String mensaje_comentario) {
		this.mensaje_comentario = mensaje_comentario;
	}


	public int getEstado() {
		return estado;
	}


	public void setEstado(int estado) {
		this.estado = estado;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	
	
	
	
	
}
