package idat.proyecto.veterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import idat.proyecto.veterinaria.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
