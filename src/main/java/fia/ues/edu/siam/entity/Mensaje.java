package fia.ues.edu.siam.entity;

import java.util.Date;

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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.sun.xml.txw2.annotation.XmlCDATA;
import com.sun.xml.txw2.annotation.XmlElement;
import com.sun.xml.txw2.annotation.XmlValue;

@Entity
@Table(name="mensaje")
public class Mensaje {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id_mensajes;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_emisor", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users user_emisor;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_receptor", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users user_receptor;

	@Column(name="contenido")
	private String contenido;
	
	@Temporal(TemporalType.DATE)
	@Column(name="fecha", nullable=true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	
	@Column(name="estado", nullable=true)
	private boolean estado;

	public Mensaje() {
		super();
	}

	public Mensaje(Users user_emisor, Users user_receptor, String contenido, Date fecha) {
		super();
		this.user_emisor = user_emisor;
		this.user_receptor = user_receptor;
		this.contenido = contenido;
		this.fecha = fecha;
		this.estado=false;
	}


	public int getId_mensajes() {
		return id_mensajes;
	}

	public void setId_mensajes(int id_mensajes) {
		this.id_mensajes = id_mensajes;
	}



	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@JsonIgnore
	public Users getUser_emisor() {
		return user_emisor;
	}

	@JsonSerialize
	public String username_emisor() {
		return user_receptor.getUsername();
	}

	public void setUser_emisor(Users user_emisor) {
		this.user_emisor = user_emisor;
	}

	@JsonIgnore
	public Users getUser_receptor() {
		return user_receptor;
	}
	
	public void setUser_receptor(Users user_receptor) {
		this.user_receptor = user_receptor;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}


	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	
	
	
	

}
