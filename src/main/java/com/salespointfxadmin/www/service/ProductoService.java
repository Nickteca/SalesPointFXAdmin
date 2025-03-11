package com.salespointfxadmin.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.Producto;
import com.salespointfxadmin.www.repositorie.ProductoRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {
	private final ProductoRepo pr;

	public List<Producto> getAllProductos() {
		return pr.findAll();
	}
}
