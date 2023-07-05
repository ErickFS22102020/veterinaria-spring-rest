package idat.proyecto.veterinaria.custom;

import java.time.LocalDateTime;

public interface BoletaCustom {
	
	public Integer getId();
	public LocalDateTime getFecha_creacion();
	public Double getIgv();
	public Double getPrecio_final();
	public Double getSub_total();
	public Boolean getEliminado();
	public Integer getCliente_id();
}
