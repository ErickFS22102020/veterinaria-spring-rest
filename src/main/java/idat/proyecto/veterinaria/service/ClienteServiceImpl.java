package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import idat.proyecto.veterinaria.cloud.UploadCloudStorage;
import idat.proyecto.veterinaria.entity.Cliente;
import idat.proyecto.veterinaria.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	private UploadCloudStorage cloudStorage = new UploadCloudStorage("clientes/","vet-cliente-");

	@Override
	@Transactional
	public void insert(Cliente cliente) {
		repository.save(cliente);
	}

	@Override
	@Transactional
	public void update(Integer id, Cliente cliente) {
		Cliente clienteFound = findById(id);
		if (clienteFound != null) {
			cliente.setId(id);
			cliente.setFoto(clienteFound.getFoto());
			repository.save(cliente);
		}
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		Cliente cliente = findById(id);
		if (cliente != null) {
			cliente.setEliminado(true);
		}
	}
	
	@Override
	@Transactional
	public void setFoto(Integer id, MultipartFile file) {
		Cliente cliente = findById(id);
		if (cliente != null) {
			cliente.setId(id);
			cliente.setFoto(cloudStorage.uploadImage(id.toString(), file));
		}
	    
	}
	
	@Override
	public Cliente findById(Integer id) {
		Cliente cliente = repository.findById(id).orElse(null);
		if(cliente != null && !cliente.getEliminado()) {
			return cliente;
		}
		return null;
	}
	
	@Override
	public Collection<Cliente> findAll() {
		return repository.findAll().stream().filter(cliente -> !cliente.getEliminado()).collect(Collectors.toList());
	}

}
