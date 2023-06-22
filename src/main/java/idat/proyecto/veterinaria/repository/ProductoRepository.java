package idat.proyecto.veterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import idat.proyecto.veterinaria.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{

}
