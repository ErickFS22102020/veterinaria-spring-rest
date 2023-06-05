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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import idat.proyecto.veterinaria.entity.Mascota;
import idat.proyecto.veterinaria.service.MascotaService;

@RestController
@RequestMapping("/mascotas")
public class MascotaController {

	@Autowired
	private MascotaService service;
	
	@GetMapping
	public Collection<Mascota> findAllMascotas() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Mascota findByIdMascota(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	public void insertMascota(@RequestBody Mascota mascota) {
		service.insert(mascota);
	}
	
	@PutMapping("/{id}")
	public void updateMascota(@PathVariable("id") Integer id, @RequestBody Mascota mascota) {
		service.update(id, mascota);
	}
	
	@DeleteMapping("/{id}")
	public void deleteMascota(@PathVariable("id") Integer id) {
		service.delete(id);
	}
	
	@PostMapping("/setFoto/{id}")
    public void uploadImage(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
        service.setFoto(id, file);
    }
}
