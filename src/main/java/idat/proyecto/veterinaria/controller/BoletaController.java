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
	public ResponseEntity<?> findAllBoletas() {
		return service.findAll();
	}
	
	@GetMapping("/custom")
	public ResponseEntity<?> findAllBoletasCustom() {
		return service.findAllCustom();
	}
	
	@GetMapping("/mapper")
	public ResponseEntity<?> findAllBoletasMapper() {
		return service.findAllMapper();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdBoleta(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> insertBoleta(@RequestBody Boleta boleta) {
		return service.insert(boleta);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateBoleta(@PathVariable("id") Integer id, @RequestBody Boleta boleta) {
		return service.update(id, boleta);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBoleta(@PathVariable("id") Integer id) {
		return service.delete(id);
	}
	
}
