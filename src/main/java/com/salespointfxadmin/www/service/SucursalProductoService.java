package com.salespointfxadmin.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.Categoria;
import com.salespointfxadmin.www.model.Producto;
import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.model.SucursalProducto;
import com.salespointfxadmin.www.repositorie.SucursalProductoRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalProductoService {
	private final SucursalProductoRepo spr;
	private final ProductoService ps;
	// private final SucursalService ss;

	public List<SucursalProducto> getAllProductosSucursalActive(Sucursal sucursal) {
		return spr.findBySucursal(sucursal);
	}

	public SucursalProducto saveSucursalProducto(float precio, boolean vendible, Categoria categporia, Producto p, Sucursal sucursal) {
		SucursalProducto sp = new SucursalProducto(0, precio, vendible, p, categporia, sucursal);
		return spr.save(sp);
	}

	@Transactional
	public void insertarSucursalProductos(Sucursal sucursal) {
		List<Producto> productos = ps.getAllProductos();
		for (Producto producto : productos) {
			spr.save(new SucursalProducto(0, 0, true, producto, new Categoria((short) 1), sucursal));
		}

	}

}
