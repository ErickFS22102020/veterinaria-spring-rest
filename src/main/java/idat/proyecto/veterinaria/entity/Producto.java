package idat.proyecto.veterinaria.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="producto")
public class Producto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
	
	@Column(name = "fecha_creacion")
    private LocalDate fecha_creacion;
	
	@Column(name = "nombre")
    private String nombre;

	@Column(name = "descripcion")
    private String descripcion;
	
	@Column(name = "precio")
    private Double precio;
	
	@Column(name = "stock")
    private Integer stock;
	
	@Column(name = "foto")
    private String foto;
	
	@Column(name = "eliminado")
    private Boolean eliminado;

	@ManyToOne
	@JoinColumn(name="categoria_id")
	private Categoria categoria;
	
	@JsonIgnore
	@OneToMany(mappedBy="producto")
    private Set<DetalleBoleta> ventas;
	
	public Producto() {}
	
	@PrePersist
	public void prePersist() {
		fecha_creacion = LocalDate.now();
		eliminado = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(LocalDate fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Boolean getEliminado() {
		return eliminado;
	}

	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Set<DetalleBoleta> getVentas() {
		return ventas;
	}

	public void setVentas(Set<DetalleBoleta> ventas) {
		this.ventas = ventas;
	}

}
