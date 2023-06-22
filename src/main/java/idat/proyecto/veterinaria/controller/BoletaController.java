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

import idat.proyecto.veterinaria.entity.Boleta;
import idat.proyecto.veterinaria.service.BoletaService;

@RestController
@RequestMapping("/boletas")
public class BoletaController {
	
	@Autowired
	private BoletaService service;
	
	@GetMapping
	public ResponseEntity<?> findAllVentas() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdVenta(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> insertVenta(@RequestBody Boleta boleta) {
		return service.insert(boleta);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateVenta(@PathVariable("id") Integer id, @RequestBody Boleta boleta) {
		return service.update(id, boleta);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteVenta(@PathVariable("id") Integer id) {
		return service.delete(id);
	}
	
}
