package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idat.proyecto.veterinaria.entity.Categoria;
import idat.proyecto.veterinaria.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService{

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	@Transactional
	public ResponseEntity<?> insert(Categoria categoria) {
		categoriaRepository.save(categoria);
		return new ResponseEntity<>("Categoria create!", HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Categoria categoria) {
		
		ResponseEntity<?> statusCategoria = findById(id);
		if (statusCategoria.getStatusCode() != HttpStatus.OK) return statusCategoria;

		categoria.setId(id);
		categoria.setEliminado(false);
		categoriaRepository.save(categoria);
		return new ResponseEntity<>("Categoria update!", HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusCategoria = findById(id);
		if (statusCategoria.getStatusCode() != HttpStatus.OK) return statusCategoria;
		Categoria categoriaFound = (Categoria) statusCategoria.getBody();
		categoriaFound.setEliminado(true);
		
		return new ResponseEntity<>("Categoria delete!", HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Categoria categoria = categoriaRepository.findById(id).orElse(null);
		if(categoria == null || categoria.getEliminado()) {
			return new ResponseEntity<>("Categoria " + id + " not found!", HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(categoria, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAll() {
		Collection<Categoria> coleccion = categoriaRepository.findAll().stream().filter(categoria -> !categoria.getEliminado()).collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}

}