package fia.ues.edu.siam.Services;

import java.util.List;

import fia.ues.edu.siam.entity.Mensaje;

public interface MensajeService {
	
	abstract public Mensaje updateMensaje(Mensaje mensaje);
	abstract public List<Mensaje> findAllMessage(int user_to, int user_from);
	abstract public void visto(int user_to, int user_from);
	
	
}
