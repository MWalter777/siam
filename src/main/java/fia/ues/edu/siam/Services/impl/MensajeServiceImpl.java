package fia.ues.edu.siam.Services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fia.ues.edu.siam.Services.MensajeService;
import fia.ues.edu.siam.entity.Mensaje;
import fia.ues.edu.siam.repository.MensajeRepository;

@Service("mensajeServiceImpl")
public class MensajeServiceImpl implements MensajeService{
	
	@Autowired
	@Qualifier("mensajeRepository")
	private MensajeRepository mensajeRepository;

	@Override
	public Mensaje updateMensaje(Mensaje mensaje) {
		return mensajeRepository.save(mensaje);
	}

	@Override
	public List<Mensaje> findAllMessage(int user_to, int user_from) {
		return mensajeRepository.findAllMyMessages(user_to, user_from, user_from, user_to);
	}
	
	@Override
	public void visto(int user_to, int user_from) {
		mensajeRepository.visto(user_from,user_to);
	}
	

}
