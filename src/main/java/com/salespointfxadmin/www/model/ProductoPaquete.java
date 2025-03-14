package com.salespointfxadmin.www.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoPaquete implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Short idProductoPaquete;

	@JoinColumn(name = "paquete", referencedColumnName = "idProducto")
	@ManyToOne(optional = false)
	private Producto paquete;

	@Column(nullable = false)
	private float cantidad;

	@JoinColumn(name = "productoPaquete", referencedColumnName = "idProducto")
	@ManyToOne(optional = false)
	private Producto productoPaquete;

	public ProductoPaquete(Producto paquete, float cantidad, Producto productoPaquete) {
		super();
		this.paquete = paquete;
		this.cantidad = cantidad;
		this.productoPaquete = productoPaquete;
	}

}
