package idat.proyecto.veterinaria.service;

import org.springframework.http.ResponseEntity;

import idat.proyecto.veterinaria.entity.Usuario;

public interface LoginService {
	
	public abstract ResponseEntity<?> Authentication(Usuario usuario);
}
