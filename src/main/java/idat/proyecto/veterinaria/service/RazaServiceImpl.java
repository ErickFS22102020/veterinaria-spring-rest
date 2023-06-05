package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idat.proyecto.veterinaria.entity.Raza;
import idat.proyecto.veterinaria.repository.RazaRepository;

@Service
public class RazaServiceImpl implements RazaService {
	
	@Autowired
	private RazaRepository repository;

	@Override
	@Transactional
	public void insert(Raza raza) {
		repository.save(raza);
		
	}

	@Override
	@Transactional
	public void update(Integer id, Raza raza) {
		Raza razaFound = findById(id);
		if (razaFound != null) {
			raza.setId(id);
			repository.save(raza);
		}
		
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		Raza raza = findById(id);
		if (raza != null) {
			raza.setEliminado(true);
		}
		
	}

	@Override
	public Raza findById(Integer id) {
		Raza raza = repository.findById(id).orElse(null);
		if(raza != null && !raza.getEliminado()) {
			return raza;
		}
		return null;
	}

	@Override
	public Collection<Raza> findAll() {
		return repository.findAll().stream().filter(raza -> !raza.getEliminado()).collect(Collectors.toList());
	}

}
