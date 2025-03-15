package com.salespointfxadmin.www.DTO;

import com.salespointfxadmin.www.model.Producto;

import lombok.Data;

@Data
public class PaqueteProductoDto {
	private Integer idPaquetProducto;
	private Producto paquete;
	private float cantidad;
	private Producto producto;
}
