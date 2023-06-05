package idat.proyecto.veterinaria.service;

import java.util.Collection;

import org.springframework.web.multipart.MultipartFile;

import idat.proyecto.veterinaria.entity.Cliente;

public interface ClienteService {
	
	public abstract void insert(Cliente cliente);
	public abstract void update(Integer id, Cliente cliente);
	public abstract void delete(Integer id);
	public abstract void setFoto(Integer id, MultipartFile file);
	public abstract Cliente findById(Integer id);
	public abstract Collection<Cliente> findAll();
	
}
