package fia.ues.edu.siam.Services;

import java.util.List;

import fia.ues.edu.siam.entity.Empresa;


public interface EmpresaService {

	public abstract Empresa updateEmpresa(Empresa empresa);
	public abstract Empresa findLast();
	public abstract int countEmpresa();
	public abstract List<Empresa> findAll();
	
}
