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
import idat.proyecto.veterinaria.entity.Cliente;
import idat.proyecto.veterinaria.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService{

	@Autowired
	private ClienteRepository clienteRepository;
	
	private GoogleStorage storage = new GoogleStorage("clientes","vet-cliente-");

	@Override
	@Transactional
	public ResponseEntity<?> insert(Cliente cliente) {
		clienteRepository.save(cliente);
		return new ResponseEntity<>("Cliente create!", HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Cliente cliente) {
		
		ResponseEntity<?> statusCliente = findById(id);
		if (statusCliente.getStatusCode() != HttpStatus.OK) return statusCliente;
		Cliente clienteFound = (Cliente) statusCliente.getBody();
		
		cliente.setId(id);
		cliente.setFecha_creacion(clienteFound.getFecha_creacion());
		cliente.setEliminado(false);
		clienteRepository.save(cliente);
		return new ResponseEntity<>("Cliente update!", HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusCliente = findById(id);
		if (statusCliente.getStatusCode() != HttpStatus.OK) return statusCliente;
		Cliente clienteFound = (Cliente) statusCliente.getBody();
		clienteFound.setEliminado(true);
		
		return new ResponseEntity<>("Cliente delete!", HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Cliente cliente = clienteRepository.findById(id).orElse(null);
		if(cliente == null || cliente.getEliminado()) {
			return new ResponseEntity<>("Cliente " + id + " not found!", HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAll() {
		Collection<Cliente> coleccion = clienteRepository.findAll().stream().filter(cliente -> !cliente.getEliminado()).collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	@Transactional
	public ResponseEntity<?> setFoto(Integer id, MultipartFile file) {
		
		ResponseEntity<?> statusCliente = findById(id);
		if (statusCliente.getStatusCode() != HttpStatus.OK) return statusCliente;
		Cliente clienteFound = (Cliente) statusCliente.getBody();
		
		try {
			clienteFound.setFoto(storage.uploadImage(id.toString(), file));
			return new ResponseEntity<>("Foto Cliente saved!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Ocurrio un error, intente nuevamente...", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
