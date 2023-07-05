package idat.proyecto.veterinaria.mapper;

import java.time.LocalDateTime;

public interface TratamientoMapper {
	
	public Integer getId();
	public String getCliente();
	public String getFoto_cliente();
	public Integer getCelular();
	public String getMascota();
	public String getFoto_mascota();
	public String getTipo();
	public LocalDateTime getFecha();
	public Integer getCitado();
	public Double getPrecio();
}
