package com.salespointfxadmin.www.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.Venta;

public interface VentaRepo extends JpaRepository<Venta, Integer> {
	Venta findByFolio(String folio);
}
