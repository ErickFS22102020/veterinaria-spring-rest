package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idat.proyecto.veterinaria.compound.DetalleBoletaId;
import idat.proyecto.veterinaria.entity.Boleta;
import idat.proyecto.veterinaria.entity.DetalleBoleta;
import idat.proyecto.veterinaria.entity.Producto;
import idat.proyecto.veterinaria.repository.BoletaRepository;

@Service
public class BoletaServiceImpl implements BoletaService{

	@Autowired
	private BoletaRepository boletaRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProductoService productoService;

	@Override
	@Transactional
	public ResponseEntity<?> insert(Boleta boleta) {
		
		ResponseEntity<?> statusCliente = clienteService.findById(boleta.getCliente().getId());
		if (statusCliente.getStatusCode() != HttpStatus.OK) return statusCliente;
		
		Set<DetalleBoleta> detalles = new HashSet<>();
		
		for(DetalleBoleta detalleBoleta : boleta.getDetalles()) {
			
			ResponseEntity<?> statusProducto = productoService.findById(detalleBoleta.getProducto().getId());
			if (statusProducto.getStatusCode() != HttpStatus.OK) return statusProducto;
			Producto producto = (Producto) statusProducto.getBody();
			
			detalleBoleta.setBoleta(boleta);
			detalleBoleta.setProducto(producto);
			detalleBoleta.calcularTotal();
			detalles.add(detalleBoleta);
		}
		
		boleta.setDetalles(detalles);
		boleta.calcularPrecioFinal();
		
		boletaRepository.save(boleta);
		return new ResponseEntity<>("Boleta create!", HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Boleta boleta) {
		
		ResponseEntity<?> statusBoleta = findById(id);
		if (statusBoleta.getStatusCode() != HttpStatus.OK) return statusBoleta;
		Boleta boletaFound = (Boleta) statusBoleta.getBody();
		
		ResponseEntity<?> statusCliente = clienteService.findById(boleta.getCliente().getId());
		if (statusCliente.getStatusCode() != HttpStatus.OK) return statusCliente;
		
		boleta.setId(id);
		boleta.setFecha_creacion(boletaFound.getFecha_creacion());
		boleta.setEliminado(false);
		
		Set<DetalleBoleta> detalles = new HashSet<>();
		
		for(DetalleBoleta detalleBoleta : boleta.getDetalles()) {
			
			ResponseEntity<?> statusProducto = productoService.findById(detalleBoleta.getProducto().getId());
			if (statusProducto.getStatusCode() != HttpStatus.OK) return statusProducto;
			Producto producto = (Producto) statusProducto.getBody();
			
	        DetalleBoletaId idDetalle = new DetalleBoletaId();
	        idDetalle.setBoleta_id(boleta.getId());
	        idDetalle.setProducto_id(producto.getId());
	        detalleBoleta.setId(idDetalle);
			detalleBoleta.setBoleta(boleta);
			detalleBoleta.setProducto(producto);
			detalleBoleta.calcularTotal();
			detalles.add(detalleBoleta);
		}
		
		boleta.setDetalles(detalles);
		boleta.calcularPrecioFinal();
		
		boletaRepository.save(boleta);
		return new ResponseEntity<>("Boleta update!", HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusBoleta = findById(id);
		if (statusBoleta.getStatusCode() != HttpStatus.OK) return statusBoleta;
		Boleta boletaFound = (Boleta) statusBoleta.getBody();
		boletaFound.setEliminado(true);
		
		return new ResponseEntity<>("Boleta delete!", HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Boleta boleta = boletaRepository.findById(id).orElse(null);
		if(boleta == null || boleta.getEliminado()) {
			return new ResponseEntity<>("Boleta " + id + " not found!", HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(boleta, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAll() {
		Collection<Boleta> coleccion = boletaRepository.findAll().stream().filter(boleta -> !boleta.getEliminado()).collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}

}


//@Override
//@Transactional
//public ResponseEntity<?> insert(Boleta boleta) {
//	
//	boleta.setDetalles(boleta.getDetalles().stream().map(detalle -> {
//		Producto producto = productoRepository.findById(detalle.getProducto().getId()).orElse(null);
//		detalle.setBoleta(boleta);
//		detalle.setProducto(producto);
//		detalle.calcularTotal();
//		System.out.println("Pass aqui 1");
//		return detalle;
//	}).collect(Collectors.toSet()));
//	boleta.calcularPrecioFinal();
//	System.out.println("Pass aqui 2");
//	boletaRepository.save(boleta);
//	
//	return new ResponseEntity<>("Boleta create!", HttpStatus.CREATED);
//}
//
//@Override
//@Transactional
//public ResponseEntity<?> update(Integer id, Boleta boleta) {
//    Boleta boletaFound = boletaRepository.findById(id).orElse(null);
//    if (boletaFound != null) {
//        boleta.setId(id);
//        boleta.setFecha_creacion(boletaFound.getFecha_creacion());
//
//        boleta.setDetalles(boleta.getDetalles().stream().map(detalle -> {
//            Producto producto = productoRepository.findById(detalle.getProducto().getId()).orElse(null);
//            DetalleBoletaId idDetalle = new DetalleBoletaId();
//            idDetalle.setBoleta_id(boleta.getId());
//            idDetalle.setProducto_id(producto.getId());
//            detalle.setId(idDetalle);
//            detalle.setBoleta(boleta);
//            detalle.setProducto(producto);
//            detalle.calcularTotal();
//            return detalle;
//        }).collect(Collectors.toSet()));
//        
//        boleta.calcularPrecioFinal();
//        boletaRepository.save(boleta);
//        return new ResponseEntity<>("Boleta update!", HttpStatus.OK);
//    }
//    return new ResponseEntity<>("Boleta " + id + " not found!", HttpStatus.NOT_FOUND);
//}
