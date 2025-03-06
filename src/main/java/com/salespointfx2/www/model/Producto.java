package com.salespointfx2.www.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Short idProducto;

	@Column(length = 60, nullable = false, unique = true)
	private String nombreProducto;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean esPaquete;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "paquete", cascade = CascadeType.ALL)
	private List<ProductoPaquete> paquete;

	public Producto(Short idProducto, String nombreProducto, Boolean esPaquete) {
		super();
		this.idProducto = idProducto;
		this.nombreProducto = nombreProducto;
		this.esPaquete = esPaquete;
		this.createdAt = LocalDateTime.now();
	}

}
