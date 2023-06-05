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

import idat.proyecto.veterinaria.entity.Cliente;
import idat.proyecto.veterinaria.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService service;
	
	@GetMapping
	public Collection<Cliente> findAllClientes() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Cliente findByIdCliente(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	public void insertCliente(@RequestBody Cliente cliente) {
		service.insert(cliente);
	}
	
	@PutMapping("/{id}")
	public void updateCliente(@PathVariable("id") Integer id, @RequestBody Cliente cliente) {
		service.update(id, cliente);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCliente(@PathVariable("id") Integer id) {
		service.delete(id);
	}
	
	@PostMapping("/setFoto/{id}")
    public void uploadImage(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
        service.setFoto(id, file);
    }
	
}
