package idat.proyecto.veterinaria.service;

import org.springframework.http.ResponseEntity;

import idat.proyecto.veterinaria.entity.DetalleBoleta;

public interface DetalleBoletaService {
	
    public abstract ResponseEntity<?> insert(DetalleBoleta detalleBoleta);
    public abstract ResponseEntity<?> update(Integer boletaId, Integer productoId, DetalleBoleta detalleBoleta);
    public abstract ResponseEntity<?> delete(Integer boletaId, Integer productoId);
    public abstract ResponseEntity<?> findById(Integer boletaId, Integer productoId);
    public abstract ResponseEntity<?> findAll();
    public abstract ResponseEntity<?> findAllCustom();
	public abstract ResponseEntity<?> findAllMapper();
}
