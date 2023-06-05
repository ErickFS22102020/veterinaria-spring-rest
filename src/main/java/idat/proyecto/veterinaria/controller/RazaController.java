package idat.proyecto.veterinaria.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idat.proyecto.veterinaria.entity.Raza;
import idat.proyecto.veterinaria.service.RazaService;

@RestController
@RequestMapping("/razas")
public class RazaController {

	@Autowired
	private RazaService service;
	
	@GetMapping
	public Collection<Raza> findAllRazas() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Raza findByIdRaza(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	public void insertRaza(@RequestBody Raza raza) {
		service.insert(raza);
	}
	
	@PutMapping("/{id}")
	public void updateRaza(@PathVariable("id") Integer id, @RequestBody Raza raza) {
		service.update(id, raza);
	}
	
	@DeleteMapping("/{id}")
	public void deleteRaza(@PathVariable("id") Integer id) {
		service.delete(id);
	}
	
}
