package com.salespointfxadmin.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.Categoria;
import com.salespointfxadmin.www.model.Producto;
import com.salespointfxadmin.www.model.ProductoPaquete;
import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.model.SucursalProducto;
import com.salespointfxadmin.www.repositorie.ProductoRepo;
import com.salespointfxadmin.www.repositorie.SucursalProductoRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {
	private final ProductoRepo pr;
	private final ProductoPaqueteService pps;
	private final SucursalProductoRepo spr;

	public List<Producto> getAllProductos() {
		return pr.findAll();
	}

	public List<Producto> getProductos() {
		return pr.findByEsPaqueteFalse();
	}

	public List<Producto> getPaquetes() {
		return pr.findByEsPaqueteTrue();
	}

	public Producto save(Producto p) {
		return pr.save(p);
	}
	
	public Producto findByProducto(String np) {
		return pr.findByNombreProducto(np);
	}

	@Transactional
	public SucursalProducto saveProductoPaquete(Producto p, List<ProductoPaquete> lpp, float precio, Categoria categoria,boolean vendible, Sucursal sucursal) {
		Producto producto = save(p);
		for (ProductoPaquete productoPaquete : lpp) {
			productoPaquete.setPaquete(p);
			pps.saveProductoPaqute(productoPaquete);

		}
		
		return spr.save(new SucursalProducto(0, precio, vendible, producto, categoria, sucursal));
	}
	
}
