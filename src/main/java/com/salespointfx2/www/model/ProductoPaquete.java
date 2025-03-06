package com.salespointfx2.www.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
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
}
