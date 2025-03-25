package com.salespointfxadmin.www.repositorie;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.enums.NombreFolio;
import com.salespointfxadmin.www.model.MovimientoInventario;
import com.salespointfxadmin.www.model.Sucursal;

public interface MovimientoInventarioRepo extends JpaRepository<MovimientoInventario, Integer> {
	List<MovimientoInventario> findBySucursalAndCreatedAtBetweenAndNombreFolio(Sucursal sucursal, LocalDateTime startDate, LocalDateTime endDate, NombreFolio nombreFolio);
	MovimientoInventario findByFolio(String folio);
}
