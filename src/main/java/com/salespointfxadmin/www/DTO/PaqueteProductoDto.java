package com.salespointfxadmin.www.DTO;

import com.salespointfxadmin.www.model.Producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaqueteProductoDto {
	private Integer idPaquetProducto;
	private Short idSucursalProducto;
	private String descripcion;
	private Float cantidad;
	public PaqueteProductoDto(Short idSucursalProducto, String descripcion, Float cantidad) {
		super();
		this.idSucursalProducto = idSucursalProducto;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
	}
	
	
}
