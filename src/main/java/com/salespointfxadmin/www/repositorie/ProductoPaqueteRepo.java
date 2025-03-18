package com.salespointfxadmin.www.repositorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.Producto;
import com.salespointfxadmin.www.model.ProductoPaquete;

public interface ProductoPaqueteRepo extends JpaRepository<ProductoPaquete, Integer> {
	List<ProductoPaquete> findByPaquete(Producto paquete);
}
