package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idat.proyecto.veterinaria.entity.Raza;
import idat.proyecto.veterinaria.repository.RazaRepository;

@Service
public class RazaServiceImpl implements RazaService{

	@Autowired
	private RazaRepository razaRepository;

	@Autowired
	private EspecieService especieService;
	
	@Override
	@Transactional
	public ResponseEntity<?> insert(Raza raza) {
		
		ResponseEntity<?> statusEspecie = especieService.findById(raza.getEspecie().getId());
		if (statusEspecie.getStatusCode() != HttpStatus.OK) return statusEspecie;
		
		razaRepository.save(raza);
		return new ResponseEntity<>("Raza create!", HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Raza raza) {
		
		ResponseEntity<?> statusRaza = findById(id);
		if (statusRaza.getStatusCode() != HttpStatus.OK) return statusRaza;
		
		ResponseEntity<?> statusEspecie = especieService.findById(raza.getEspecie().getId());
		if (statusEspecie.getStatusCode() != HttpStatus.OK) return statusEspecie;
		
		raza.setId(id);
		raza.setEliminado(false);
		razaRepository.save(raza);
		return new ResponseEntity<>("Raza update!", HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusRaza = findById(id);
		if (statusRaza.getStatusCode() != HttpStatus.OK) return statusRaza;
		Raza razaFound = (Raza) statusRaza.getBody();
		razaFound.setEliminado(true);
		
		return new ResponseEntity<>("Raza delete!", HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Raza raza = razaRepository.findById(id).orElse(null);
		if(raza == null || raza.getEliminado()) {
			return new ResponseEntity<>("Raza " + id + " not found!", HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(raza, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAll() {
		Collection<Raza> coleccion = razaRepository.findAll().stream().filter(raza -> !raza.getEliminado()).collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}

}