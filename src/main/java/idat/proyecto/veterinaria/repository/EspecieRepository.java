package idat.proyecto.veterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import idat.proyecto.veterinaria.entity.Especie;

public interface EspecieRepository extends JpaRepository<Especie, Integer>{

}
