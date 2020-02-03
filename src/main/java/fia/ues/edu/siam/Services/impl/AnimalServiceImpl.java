package fia.ues.edu.siam.Services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fia.ues.edu.siam.Services.AnimalService;
import fia.ues.edu.siam.entity.Animal;
import fia.ues.edu.siam.repository.AnimalRepository;

@Service("animalServiceImpl")
public class AnimalServiceImpl implements AnimalService{
	
	@Autowired
	@Qualifier("animalRepository")
	private AnimalRepository animalRepository;

	@Override
	public Animal findById(int id) {
		return animalRepository.findById(id);
	}

	@Override
	public List<Animal> findAll() {
		return animalRepository.findAll();
	}

	@Override
	public Animal updateAnimal(Animal animal) {
		return animalRepository.save(animal);
	}

	@Override
	public List<Animal> findAllValid(int valor) {
		return animalRepository.findByValid(valor);
	}

	@Override
	public List<Animal> findAllLastestValid(int limite) {
		return animalRepository.findByLastestValid(limite);
	}
		
	@Override
	public List<Animal> findAllLastestValid(int limite, int limite2) {
		return animalRepository.findByLastestValid(limite, limite2);
	}

	@Override
	public List<Animal> findAll(String buscar) {
		return animalRepository.findAll(buscar);
	}
		
	
	
}
