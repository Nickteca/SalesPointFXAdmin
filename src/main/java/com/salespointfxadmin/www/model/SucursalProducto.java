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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SucursalProducto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short idSucursalProducto;

	@Column(nullable = false)
	private float inventario;

	@Column(nullable = false)
	private float precio;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
	private boolean vendible;

	@JoinColumn(name = "producto", referencedColumnName = "idProducto")
	@ManyToOne(optional = false)
	private Producto producto;

	@JoinColumn(name = "categoria", referencedColumnName = "idCategoria")
	@ManyToOne(optional = false)
	private Categoria categoria;

	@JoinColumn(name = "sucursal", referencedColumnName = "idSucursal")
	@ManyToOne(optional = false)
	private Sucursal sucursal;

	public SucursalProducto(float inventario, float precio, boolean vendible, Producto producto, Categoria categoria, Sucursal sucursal) {
		super();
		this.inventario = inventario;
		this.precio = precio;
		this.vendible = vendible;
		this.producto = producto;
		this.categoria = categoria;
		this.sucursal = sucursal;
	}

	@Override
	public String toString() {
		return  idSucursalProducto + " "+producto.getNombreProducto();
	}
	

}
