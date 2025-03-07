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
	
	@JoinColumn(name = "categoria", referencedColumnName = "idCategoria")
	@ManyToOne(optional = false)
	private Categoria categoria;
	
	@JoinColumn(name = "sucursal", referencedColumnName = "idSucursal")
	@ManyToOne(optional = false)
	private Sucursal sucursal;
	

}
