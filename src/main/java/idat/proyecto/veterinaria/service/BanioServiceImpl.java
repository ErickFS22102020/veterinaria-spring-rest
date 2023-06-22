package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import idat.proyecto.veterinaria.cloud.GoogleStorage;
import idat.proyecto.veterinaria.entity.Banio;
import idat.proyecto.veterinaria.repository.BanioRepository;

@Service
public class BanioServiceImpl implements BanioService{

	@Autowired
	private BanioRepository banioRepository;
	
	@Autowired
	private MascotaService mascotaService;
	
	private GoogleStorage storageEntrada = new GoogleStorage("banios","vet-banio-entrada-");
	private GoogleStorage storageSalida = new GoogleStorage("banios","vet-banio-salida-");

	@Override
	@Transactional
	public ResponseEntity<?> insert(Banio banio) {
		
		ResponseEntity<?> statusMascota = mascotaService.findById(banio.getMascota().getId());
		if (statusMascota.getStatusCode() != HttpStatus.OK) return statusMascota;
		
		banioRepository.save(banio);
		return new ResponseEntity<>("Banio create!", HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Banio banio) {
		
		ResponseEntity<?> statusBanio = findById(id);
		if (statusBanio.getStatusCode() != HttpStatus.OK) return statusBanio;
		Banio banioFound = (Banio) statusBanio.getBody();
		
		ResponseEntity<?> statusMascota = mascotaService.findById(banio.getMascota().getId());
		if (statusMascota.getStatusCode() != HttpStatus.OK) return statusMascota;
		
		banio.setId(id);
		banio.setFecha_creacion(banioFound.getFecha_creacion());
		banio.setEliminado(false);
		banioRepository.save(banio);
		return new ResponseEntity<>("Banio update!", HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusBanio = findById(id);
		if (statusBanio.getStatusCode() != HttpStatus.OK) return statusBanio;
		Banio banioFound = (Banio) statusBanio.getBody();
		banioFound.setEliminado(true);
		
		return new ResponseEntity<>("Banio delete!", HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Banio banio = banioRepository.findById(id).orElse(null);
		if(banio == null || banio.getEliminado()) {
			return new ResponseEntity<>("Banio " + id + " not found!", HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(banio, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAll() {
		Collection<Banio> coleccion = banioRepository.findAll().stream().filter(banio -> !banio.getEliminado()).collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	@Transactional
	public ResponseEntity<?> setFotoEntrada(Integer id, MultipartFile file) {
		
		ResponseEntity<?> statusBanio = findById(id);
		if (statusBanio.getStatusCode() != HttpStatus.OK) return statusBanio;
		Banio banioFound = (Banio) statusBanio.getBody();
		
		try {
			banioFound.setFoto_entrada(storageEntrada.uploadImage(id.toString(), file));
			return new ResponseEntity<>("FotoEntrada Banio saved!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Ocurrio un error, intente nuevamente...", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	@Transactional
	public ResponseEntity<?> setFotoSalida(Integer id, MultipartFile file) {
		
		ResponseEntity<?> statusBanio = findById(id);
		if (statusBanio.getStatusCode() != HttpStatus.OK) return statusBanio;
		Banio banioFound = (Banio) statusBanio.getBody();
		
		try {
			banioFound.setFoto_salida(storageSalida.uploadImage(id.toString(), file));
			return new ResponseEntity<>("FotoSalida Banio saved!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Ocurrio un error, intente nuevamente...", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
