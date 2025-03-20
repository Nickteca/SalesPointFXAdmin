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
public class MovimientoInventarioDetalle implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer idMovimientoInventarioDetalle;

	@Column(nullable = false)
	private short unidades;

	@JoinColumn(name = "movimientoInventario", referencedColumnName = "idMovimientoInventario")
	@ManyToOne(optional = false)
	private MovimientoInventario movimientoInventario;

	@JoinColumn(name = "sucursalProducto", referencedColumnName = "idSucursalProducto")
	@ManyToOne(optional = false)
	private SucursalProducto sucursalProducto;

	public MovimientoInventarioDetalle(Integer idMovimientoInventarioDetalle, short unidades,
			SucursalProducto sucursalProducto) {
		super();
		this.idMovimientoInventarioDetalle = idMovimientoInventarioDetalle;
		this.unidades = unidades;
		this.sucursalProducto = sucursalProducto;
	}
	
	

}
