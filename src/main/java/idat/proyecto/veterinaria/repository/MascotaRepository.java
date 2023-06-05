package idat.proyecto.veterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import idat.proyecto.veterinaria.entity.Mascota;

public interface MascotaRepository extends JpaRepository<Mascota, Integer>{

}
