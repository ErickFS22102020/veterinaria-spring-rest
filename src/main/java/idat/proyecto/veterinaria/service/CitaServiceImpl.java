package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idat.proyecto.veterinaria.entity.Cita;
import idat.proyecto.veterinaria.repository.CitaRepository;

@Service
public class CitaServiceImpl implements CitaService{

	@Autowired
	private CitaRepository citaRepository;
	
	@Autowired
	private MascotaService mascotaService;

	@Override
	@Transactional
	public ResponseEntity<?> insert(Cita cita) {
		
		ResponseEntity<?> statusMascota = mascotaService.findById(cita.getMascota().getId());
		if (statusMascota.getStatusCode() != HttpStatus.OK) return statusMascota;
		
		citaRepository.save(cita);
		return new ResponseEntity<>("Cita create!", HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Cita cita) {
		
		ResponseEntity<?> statusCita = findById(id);
		if (statusCita.getStatusCode() != HttpStatus.OK) return statusCita;
		
		ResponseEntity<?> statusMascota = mascotaService.findById(cita.getMascota().getId());
		if (statusMascota.getStatusCode() != HttpStatus.OK) return statusMascota;
		
		cita.setId(id);
		cita.setEliminado(false);
		citaRepository.save(cita);
		return new ResponseEntity<>("Cita update!", HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusCita = findById(id);
		if (statusCita.getStatusCode() != HttpStatus.OK) return statusCita;
		Cita citaFound = (Cita) statusCita.getBody();
		citaFound.setEliminado(true);
		
		return new ResponseEntity<>("Cita delete!", HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Cita cita = citaRepository.findById(id).orElse(null);
		if(cita == null || cita.getEliminado()) {
			return new ResponseEntity<>("Cita " + id + " not found!", HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(cita, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAll() {
		Collection<Cita> coleccion = citaRepository.findAll().stream().filter(cita -> !cita.getEliminado()).collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}

}