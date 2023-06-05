package idat.proyecto.veterinaria.service;

import java.util.Collection;

import idat.proyecto.veterinaria.entity.Especie;

public interface EspecieService {
	public abstract void insert(Especie especie);
	public abstract void update(Integer id, Especie especie);
	public abstract void delete(Integer id);
	public abstract Especie findById(Integer id);
	public abstract Collection<Especie> findAll();
}
