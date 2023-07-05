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
import idat.proyecto.veterinaria.custom.BoletaCustom;
import idat.proyecto.veterinaria.entity.Boleta;
import idat.proyecto.veterinaria.entity.DetalleBoleta;
import idat.proyecto.veterinaria.entity.Producto;
import idat.proyecto.veterinaria.mapper.BoletaMapper;
import idat.proyecto.veterinaria.repository.BoletaRepository;
import idat.proyecto.veterinaria.response.Response;

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
		return new ResponseEntity<>(Response.createMap("Boleta create!", boleta.getId()), HttpStatus.CREATED);
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
		return new ResponseEntity<>(Response.createMap("Boleta update!", boleta.getId()), HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusBoleta = findById(id);
		if (statusBoleta.getStatusCode() != HttpStatus.OK) return statusBoleta;
		Boleta boletaFound = (Boleta) statusBoleta.getBody();
		boletaFound.setEliminado(true);
		
		return new ResponseEntity<>(Response.createMap("Boleta delete!", id), HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Boleta boleta = boletaRepository.findById(id).orElse(null);
		if(boleta == null || boleta.getEliminado()) {
			return new ResponseEntity<>(Response.createMap("Boleta not found!", id), HttpStatus.NOT_FOUND);
			
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
	
	@Override
	public ResponseEntity<?> findAllCustom() {
		Collection<BoletaCustom> coleccion = boletaRepository.findAllCustom();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllMapper() {
		Collection<BoletaMapper> coleccion = boletaRepository.findAllMapper();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}

}