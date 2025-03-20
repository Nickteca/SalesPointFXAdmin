package com.salespointfxadmin.www.repositorie;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.MovimientoCaja;
import com.salespointfxadmin.www.model.Sucursal;

public interface MovimientoCajaRepo extends JpaRepository<MovimientoCaja, Integer> {
	MovimientoCaja findFirstBySucursalOrderByIdMovimientoCajaDesc(Sucursal sucursal);

	Optional<MovimientoCaja> findFirstBySucursalEstatusSucursalTrueOrderByIdMovimientoCajaDesc();
}
