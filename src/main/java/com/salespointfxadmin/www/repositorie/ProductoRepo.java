package com.salespointfxadmin.www.repositorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.Producto;

public interface ProductoRepo extends JpaRepository<Producto, Short> {
	Producto findByNombreProducto(String nombreProducto);
	List<Producto> findByEsPaqueteFalse();
	List<Producto> findByEsPaqueteTrue();
}
