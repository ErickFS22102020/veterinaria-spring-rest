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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import idat.proyecto.veterinaria.entity.Banio;
import idat.proyecto.veterinaria.service.BanioService;

@RestController
@RequestMapping("/banios")
public class BanioController {
	
	@Autowired
	private BanioService service;
	
	@GetMapping
	public ResponseEntity<?> findAllBanos() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdBano(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> insertBano(@RequestBody Banio banio) {
		return service.insert(banio);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateBano(@PathVariable("id") Integer id, @RequestBody Banio banio) {
		return service.update(id, banio);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBano(@PathVariable("id") Integer id) {
		return service.delete(id);
	}
	
	@PostMapping("/setFotoEntrada/{id}")
    public ResponseEntity<?> saveImageEntrada(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
        return service.setFotoEntrada(id, file);
    }
	
	@PostMapping("/setFotoSalida/{id}")
    public ResponseEntity<?> saveImageSalida(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
        return service.setFotoSalida(id, file);
    }
	
}

