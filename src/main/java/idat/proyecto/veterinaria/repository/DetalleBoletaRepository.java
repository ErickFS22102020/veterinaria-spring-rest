package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import idat.proyecto.veterinaria.compound.DetalleBoletaId;
import idat.proyecto.veterinaria.custom.DetalleBoletaCustom;
import idat.proyecto.veterinaria.entity.DetalleBoleta;
import idat.proyecto.veterinaria.mapper.DetalleBoletaMapper;

public interface DetalleBoletaRepository extends JpaRepository<DetalleBoleta, DetalleBoletaId> {
	
	@Query(value="SELECT * FROM detalle_boleta_custom",nativeQuery=true)
	public abstract Collection<DetalleBoletaCustom> findAllCustom();
	
	@Query(value="SELECT * FROM detalle_boleta_mapper",nativeQuery=true)
	public abstract Collection<DetalleBoletaMapper> findAllMapper();
}
