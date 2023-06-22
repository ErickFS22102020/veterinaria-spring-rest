package idat.proyecto.veterinaria.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "cita")
public class Cita implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;
	
	@Column(name = "fecha_programada")
	private LocalDateTime fecha_programada;
	
	@Column(name = "fecha_atendida")
	private LocalDateTime fecha_atendida;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "motivo")
	private String motivo;
	
	@Column(name = "eliminado")
	private Boolean eliminado;
	
	@ManyToOne
    @JoinColumn(name = "mascota_id")
	private Mascota mascota;
	                             
	public Cita() {}
	
	@PrePersist
	public void prePersist() {
		eliminado = false;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getFecha_programada() {
		return fecha_programada;
	}

	public void setFecha_programada(LocalDateTime fecha_programada) {
		this.fecha_programada = fecha_programada;
	}

	public LocalDateTime getFecha_atendida() {
		return fecha_atendida;
	}

	public void setFecha_atendida(LocalDateTime fecha_atendida) {
		this.fecha_atendida = fecha_atendida;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Boolean getEliminado() {
		return eliminado;
	}

	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}

	public Mascota getMascota() {
		return mascota;
	}

	public void setMascota(Mascota mascota) {
		this.mascota = mascota;
	}

}
