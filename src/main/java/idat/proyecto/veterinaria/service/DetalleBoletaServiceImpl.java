package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import idat.proyecto.veterinaria.compound.DetalleBoletaId;
import idat.proyecto.veterinaria.custom.DetalleBoletaCustom;
import idat.proyecto.veterinaria.entity.Boleta;
import idat.proyecto.veterinaria.entity.DetalleBoleta;
import idat.proyecto.veterinaria.entity.Producto;
import idat.proyecto.veterinaria.mapper.DetalleBoletaMapper;
import idat.proyecto.veterinaria.repository.DetalleBoletaRepository;
import idat.proyecto.veterinaria.response.Response;

@Service
public class DetalleBoletaServiceImpl implements DetalleBoletaService {
	
    @Autowired
    private DetalleBoletaRepository detalleBoletaRepository;
    
    @Autowired
    private BoletaService boletaService;
    
    @Autowired
    private ProductoService productoService;

    @Override
    @Transactional
    public ResponseEntity<?> insert(DetalleBoleta detalleBoleta) {
    	
    	ResponseEntity<?> statusBoleta = boletaService.findById(detalleBoleta.getBoleta().getId());
		if (statusBoleta.getStatusCode() != HttpStatus.OK) return statusBoleta;
		Boleta boleta = (Boleta) statusBoleta.getBody();
    	
    	ResponseEntity<?> statusProducto = productoService.findById(detalleBoleta.getProducto().getId());
		if (statusProducto.getStatusCode() != HttpStatus.OK) return statusProducto;
		Producto producto = (Producto) statusProducto.getBody();
		
		ResponseEntity<?> statusDetalleBoleta = findById(detalleBoleta.getBoleta().getId(),detalleBoleta.getProducto().getId());
        if (statusDetalleBoleta.getStatusCode() == HttpStatus.OK) {
        	return new ResponseEntity<>(Response.createMap("DetalleBoleta exists!", detalleBoleta.getBoleta().getId() + " ; " + detalleBoleta.getProducto().getId()), HttpStatus.FOUND); 
        }
		
		detalleBoleta.setBoleta(boleta);
		detalleBoleta.setProducto(producto);
		
        detalleBoletaRepository.save(detalleBoleta);
        DetalleBoletaId id = detalleBoleta.getId();
        
        
        return new ResponseEntity<>(Response.createMap("DetalleBoleta create!", id.getBoleta_id() + " ; " + id.getProducto_id()), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(Integer boletaId, Integer productoId, DetalleBoleta detalleBoleta) {
    	
        ResponseEntity<?> statusDetalleBoleta = findById(boletaId,productoId);
        if (statusDetalleBoleta.getStatusCode() != HttpStatus.OK) return statusDetalleBoleta;
        
        DetalleBoleta detalleBoletaFound = (DetalleBoleta) statusDetalleBoleta.getBody();
        detalleBoletaFound.setId(detalleBoletaFound.getId());
        
        detalleBoleta.setId(detalleBoletaFound.getId());
        detalleBoleta.setBoleta(detalleBoletaFound.getBoleta());
        detalleBoleta.setProducto(detalleBoletaFound.getProducto());
        
        detalleBoletaRepository.save(detalleBoleta);
        return new ResponseEntity<>(Response.createMap("DetalleBoleta update!", boletaId + " ; " + productoId), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Integer boletaId, Integer productoId) {
    	
        ResponseEntity<?> statusDetalleBoleta = findById(boletaId,productoId);
        if (statusDetalleBoleta.getStatusCode() != HttpStatus.OK) return statusDetalleBoleta;
        DetalleBoleta detalleBoletaFound = (DetalleBoleta) statusDetalleBoleta.getBody();
        
        detalleBoletaRepository.deleteById(detalleBoletaFound.getId());

        return new ResponseEntity<>(Response.createMap("DetalleBoleta delete!", boletaId + " ; " + productoId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findById(Integer boletaId, Integer productoId) {
    	
    	DetalleBoletaId id = new DetalleBoletaId(boletaId, productoId);
    	
        Optional<DetalleBoleta> detalleboleta = detalleBoletaRepository.findById(id);
        if (!detalleboleta.isPresent()) {
            return new ResponseEntity<>(Response.createMap("Detalleboleta not found!", id.getBoleta_id() + " ; " + id.getProducto_id()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(detalleboleta.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAll() {
        Collection<DetalleBoleta> coleccion = detalleBoletaRepository.findAll();
        if (coleccion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coleccion, HttpStatus.OK);
    }

	@Override
	public ResponseEntity<?> findAllCustom() {
		Collection<DetalleBoletaCustom> coleccion = detalleBoletaRepository.findAllCustom();
        if (coleccion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAllMapper() {
		Collection<DetalleBoletaMapper> coleccion = detalleBoletaRepository.findAllMapper();
        if (coleccion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
}
