package com.salespointfxadmin.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.Producto;
import com.salespointfxadmin.www.repositorie.ProductoRepo;
import com.salespointfxadmin.www.repositorie.SucursalProductoRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {
	private final ProductoRepo pr;
	private final SucursalProductoRepo spr;

	public List<Producto> getAllProductos() {
		return pr.findAll();
	}
	
	public List<Producto> getProductos(){
		return pr.findByEsPaqueteFalse();
	}
	
	public List<Producto> getPaquetes(){
		return pr.findByEsPaqueteFalse();
	}

	public Producto save(Producto p) {
		return pr.save(p);
	}

}
