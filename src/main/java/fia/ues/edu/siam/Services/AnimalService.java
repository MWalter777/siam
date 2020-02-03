package fia.ues.edu.siam.Services;

import java.util.List;

import fia.ues.edu.siam.entity.Animal;

public interface AnimalService {
	
	public abstract Animal findById(int id);
	public abstract List<Animal> findAll();
	public abstract List<Animal> findAllValid(int valor);
	public abstract List<Animal> findAllLastestValid(int limite);
	public abstract List<Animal> findAllLastestValid(int limite, int limite2);
	public abstract Animal updateAnimal(Animal animal);
	public abstract List<Animal> findAll(String buscar);
	

}
