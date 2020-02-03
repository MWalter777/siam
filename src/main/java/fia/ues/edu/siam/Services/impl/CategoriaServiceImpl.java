package fia.ues.edu.siam.Services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fia.ues.edu.siam.Services.CategoriaService;
import fia.ues.edu.siam.entity.Categoria;
import fia.ues.edu.siam.repository.CategoriaRepository;

@Service("categoriaServiceImpl")
public class CategoriaServiceImpl implements CategoriaService{
	
	@Autowired
	@Qualifier("categoriaRepository")
	private CategoriaRepository categoriaRepository;

	@Override
	public Categoria findById(int id) {
		return categoriaRepository.findById(id);
	}

	@Override
	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

	@Override
	public Categoria updateCategoria(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	
	

}
