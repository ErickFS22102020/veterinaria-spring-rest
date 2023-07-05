package idat.proyecto.veterinaria.mapper;

import java.time.LocalDateTime;

public interface BoletaMapper {
	
	public Integer getId();
	public String getCliente();
	public String getFoto_cliente();
	public LocalDateTime getFecha();
	public Double getSub_total();
	public Double getIgv();
	public Double getPrecio_final();
}
