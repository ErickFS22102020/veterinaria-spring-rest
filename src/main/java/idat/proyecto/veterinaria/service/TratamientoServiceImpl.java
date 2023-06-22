package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idat.proyecto.veterinaria.entity.Tratamiento;
import idat.proyecto.veterinaria.repository.TratamientoRepository;

@Service
public class TratamientoServiceImpl implements TratamientoService{

	@Autowired
	private TratamientoRepository tratamientoRepository;
	
	@Autowired
	private MascotaService mascotaService;

	@Override
	@Transactional
	public ResponseEntity<?> insert(Tratamiento tratamiento) {
		
		ResponseEntity<?> statusMascota = mascotaService.findById(tratamiento.getMascota().getId());
		if (statusMascota.getStatusCode() != HttpStatus.OK) return statusMascota;
		
		tratamientoRepository.save(tratamiento);
		return new ResponseEntity<>("Tratamiento create!", HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Tratamiento tratamiento) {
		
		ResponseEntity<?> statusTratamiento = findById(id);
		if (statusTratamiento.getStatusCode() != HttpStatus.OK) return statusTratamiento;
		Tratamiento tratamientoFound = (Tratamiento) statusTratamiento.getBody();
		
		ResponseEntity<?> statusMascota = mascotaService.findById(tratamiento.getMascota().getId());
		if (statusMascota.getStatusCode() != HttpStatus.OK) return statusMascota;
		
		tratamiento.setId(id);
		tratamiento.setFecha_creacion(tratamientoFound.getFecha_creacion());
		tratamiento.setEliminado(false);
		tratamientoRepository.save(tratamiento);
		return new ResponseEntity<>("Tratamiento update!", HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusTratamiento = findById(id);
		if (statusTratamiento.getStatusCode() != HttpStatus.OK) return statusTratamiento;
		Tratamiento tratamientoFound = (Tratamiento) statusTratamiento.getBody();
		tratamientoFound.setEliminado(true);
		
		return new ResponseEntity<>("Tratamiento delete!", HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Tratamiento tratamiento = tratamientoRepository.findById(id).orElse(null);
		if(tratamiento == null || tratamiento.getEliminado()) {
			return new ResponseEntity<>("Tratamiento " + id + " not found!", HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(tratamiento, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAll() {
		Collection<Tratamiento> coleccion = tratamientoRepository.findAll().stream().filter(tratamiento -> !tratamiento.getEliminado()).collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}

}