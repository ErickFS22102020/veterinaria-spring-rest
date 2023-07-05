package idat.proyecto.veterinaria.mapper;

import java.time.LocalDateTime;

public interface ProductoMapper {

	public Integer getId();
	public String getNombre();
	public String getCategoria();
	public String getDescripcion();
	public LocalDateTime getFecha();
	public Integer getStock();
	public Double getPrecio();
	public String getFoto();
}
