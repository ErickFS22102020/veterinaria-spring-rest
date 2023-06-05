package idat.proyecto.veterinaria.service;

import java.util.Collection;

import org.springframework.web.multipart.MultipartFile;

import idat.proyecto.veterinaria.entity.Mascota;

public interface MascotaService {

	public abstract void insert(Mascota mascota);
	public abstract void update(Integer id, Mascota mascota);
	public abstract void delete(Integer id);
	public abstract void setFoto(Integer id, MultipartFile file);
	public abstract Mascota findById(Integer id);
	public abstract Collection<Mascota> findAll();
	
}
