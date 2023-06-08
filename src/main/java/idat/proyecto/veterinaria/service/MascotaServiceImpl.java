package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import idat.proyecto.veterinaria.cloud.GoogleStorage;
import idat.proyecto.veterinaria.entity.Mascota;
import idat.proyecto.veterinaria.repository.MascotaRepository;

@Service
public class MascotaServiceImpl implements MascotaService {

	@Autowired
	private MascotaRepository repository;
	
	private GoogleStorage storage = new GoogleStorage("mascotas","vet-mascota-");
	
	@Override
	@Transactional
	public void insert(Mascota mascota) {
		repository.save(mascota);
	}
	
	@Override
	@Transactional
	public void update(Integer id, Mascota mascota) {
		Mascota mascotaFound = findById(id);
		if (mascotaFound != null) {
			mascota.setId(id);
			mascota.setFoto(mascotaFound.getFoto());
			repository.save(mascota);
		}
	}
	
	@Override
	@Transactional
	public void delete(Integer id) {
		Mascota mascota = findById(id);
		if (mascota != null) {
			mascota.setEliminado(true);
		}
	}
	
	@Override
	@Transactional
	public void setFoto(Integer id, MultipartFile file) {
		Mascota mascota = findById(id);
		if (mascota != null) {
			mascota.setId(id);
			mascota.setFoto(storage.uploadImage(id.toString(), file));
		}
	}
	
	@Override
	public Mascota findById(Integer id) {
		Mascota mascota = repository.findById(id).orElse(null);
		if(mascota != null && !mascota.getEliminado()) {
			return mascota;
		}
		return null;
	}
	
	@Override
	public Collection<Mascota> findAll() {
		return repository.findAll().stream().filter(mascota -> !mascota.getEliminado()).collect(Collectors.toList());
	}
}
