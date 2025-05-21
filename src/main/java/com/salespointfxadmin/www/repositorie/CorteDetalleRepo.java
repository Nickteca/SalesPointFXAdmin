package com.salespointfxadmin.www.repositorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.Corte;
import com.salespointfxadmin.www.model.CorteDetalle;

public interface CorteDetalleRepo extends JpaRepository<CorteDetalle, Integer> {
	List<CorteDetalle> findByCorte(Corte corte);
}
