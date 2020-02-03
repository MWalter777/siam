package fia.ues.edu.siam.Services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fia.ues.edu.siam.Services.EmpresaService;
import fia.ues.edu.siam.entity.Empresa;
import fia.ues.edu.siam.repository.EmpresaRepository;

@Service("empresaServiceImpl")
public class EmpresaServiceImp implements EmpresaService{

	@Autowired
	@Qualifier("empresaRepository")
	private EmpresaRepository empresaRepository;
	
	
	@Override
	public Empresa updateEmpresa(Empresa empresa) {
		return empresaRepository.save(empresa);
	}

	@Override
	public Empresa findLast() {
		return empresaRepository.findLast();
	}

	@Override
	public int countEmpresa() {
		return empresaRepository.countEmpresa();
	}

	@Override
	public List<Empresa> findAll() {
		return empresaRepository.findAll();
	}

}
