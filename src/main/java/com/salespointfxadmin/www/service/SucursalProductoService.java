package com.salespointfxadmin.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.Categoria;
import com.salespointfxadmin.www.model.Producto;
import com.salespointfxadmin.www.model.SucursalProducto;
import com.salespointfxadmin.www.repositorie.SucursalProductoRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalProductoService {
	private final SucursalProductoRepo spr;

	private final SucursalService ss;

	public List<SucursalProducto> getAllProductosSucursalActive() {
		return spr.findBySucursal(ss.getSucursalActive());
	}

	public SucursalProducto saveSucursalProducto(float precio, boolean vendible, Categoria categporia, Producto p) {
		SucursalProducto sp = new SucursalProducto(0, precio, vendible, p, categporia, ss.getSucursalActive());
		return spr.save(sp);
	}

}
