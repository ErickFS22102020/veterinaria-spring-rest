package idat.proyecto.veterinaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idat.proyecto.veterinaria.entity.Cita;
import idat.proyecto.veterinaria.service.CitaService;

@RestController
@RequestMapping("/citas")
public class CitaController {
	
	@Autowired
	private CitaService service;
	
	@GetMapping
	public ResponseEntity<?> findAllCitas() {
		return service.findAll();
	}
	
	@GetMapping("/custom")
	public ResponseEntity<?> findAllCitasCustom() {
		return service.findAllCustom();
	}
	
	@GetMapping("/mapper")
	public ResponseEntity<?> findAllCitasMapper() {
		return service.findAllMapper();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdCita(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> insertCita(@RequestBody Cita cita) {
		return service.insert(cita);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCita(@PathVariable("id") Integer id, @RequestBody Cita cita) {
		return service.update(id, cita);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCita(@PathVariable("id") Integer id) {
		return service.delete(id);
	}
	
}

