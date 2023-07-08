package idat.proyecto.veterinaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idat.proyecto.veterinaria.entity.DetalleBoleta;
import idat.proyecto.veterinaria.service.DetalleBoletaService;

@RestController
@RequestMapping("/detalle-boleta")
public class DetalleBoletaController {
	
    @Autowired
    private DetalleBoletaService service;

    @GetMapping
    public ResponseEntity<?> findAllDetalleboleta() {
        return service.findAll();
    }
    
    @GetMapping("/custom")
	public ResponseEntity<?> findAllDetalleboletaCustom() {
		return service.findAllCustom();
	}
	
	@GetMapping("/mapper")
	public ResponseEntity<?> findAllDetalleboletaMapper() {
		return service.findAllMapper();
	}

    @GetMapping("/{boletaId}/{productoId}")
    public ResponseEntity<?> findByIdDetalleboleta(@PathVariable("boletaId") Integer boletaId, @PathVariable("productoId") Integer productoId) {
        return service.findById(boletaId, productoId);
    }

    @PostMapping
    public ResponseEntity<?> insertDetalleboleta(@RequestBody DetalleBoleta detalleBoleta) {
        return service.insert(detalleBoleta);
    }

    @PutMapping("/{boletaId}/{productoId}")
    public ResponseEntity<?> updateDetalleboleta(@PathVariable("boletaId") Integer boletaId, @PathVariable("productoId") Integer productoId, @RequestBody DetalleBoleta detalleBoleta) {
        return service.update(boletaId, productoId, detalleBoleta);
    }

    @DeleteMapping("/{boletaId}/{productoId}")
    public ResponseEntity<?> deleteDetalleboleta(@PathVariable("boletaId") Integer boletaId, @PathVariable("productoId") Integer productoId) {
        return service.delete(boletaId, productoId);
    }
}
