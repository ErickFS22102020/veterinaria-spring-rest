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

import idat.proyecto.veterinaria.entity.Especie;
import idat.proyecto.veterinaria.service.EspecieService;

@RestController
@RequestMapping("/especies")
public class EspecieController {
	
	@Autowired
	private EspecieService service;
	
	@GetMapping
	public Collection<Especie> findAllEspecies() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Especie findByIdEspecie(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	public void insertEspecie(@RequestBody Especie especie) {
		service.insert(especie);
	}
	
	@PutMapping("/{id}")
	public void updateEspecie(@PathVariable("id") Integer id, @RequestBody Especie especie) {
		service.update(id, especie);
	}
	
	@DeleteMapping("/{id}")
	public void deleteEspecie(@PathVariable("id") Integer id) {
		service.delete(id);
	}
}
