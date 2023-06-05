package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idat.proyecto.veterinaria.entity.Especie;
import idat.proyecto.veterinaria.repository.EspecieRepository;

@Service
public class EspecieServiceImpl implements EspecieService {
	
	@Autowired
	private EspecieRepository repository;

	@Override
	@Transactional
	public void insert(Especie especie) {
		repository.save(especie);
		
	}

	@Override
	@Transactional
	public void update(Integer id, Especie especie) {
		Especie especieFound = findById(id);
		if (especieFound != null) {
			especie.setId(id);
			repository.save(especie);
		}
		
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		Especie especie = findById(id);
		if (especie != null) {
			especie.setEliminado(true);
		}
		
	}

	@Override
	public Especie findById(Integer id) {
		Especie especie = repository.findById(id).orElse(null);
		if(especie != null && !especie.getEliminado()) {
			return especie;
		}
		return null;
	}

	@Override
	public Collection<Especie> findAll() {
		return repository.findAll().stream().filter(especie -> !especie.getEliminado()).collect(Collectors.toList());
	}

}
