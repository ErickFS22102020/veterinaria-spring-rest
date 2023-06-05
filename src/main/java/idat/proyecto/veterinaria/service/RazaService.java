package idat.proyecto.veterinaria.service;

import java.util.Collection;

import idat.proyecto.veterinaria.entity.Raza;

public interface RazaService {

	public abstract void insert(Raza raza);
	public abstract void update(Integer id, Raza raza);
	public abstract void delete(Integer id);
	public abstract Raza findById(Integer id);
	public abstract Collection<Raza> findAll();
}
