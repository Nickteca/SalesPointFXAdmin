package com.salespointfxadmin.www.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaqueteProductoDto {
	private Integer idPaquetProducto;
	private Short idProducto;
	private String descripcion;
	private Float cantidad;

	public PaqueteProductoDto(Short idProducto, String descripcion, Float cantidad) {
		super();
		this.idProducto = idProducto;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
	}

}
