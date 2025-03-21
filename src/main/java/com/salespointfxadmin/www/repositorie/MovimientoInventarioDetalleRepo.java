package com.salespointfxadmin.www.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.MovimientoInventario;
import com.salespointfxadmin.www.model.MovimientoInventarioDetalle;
import java.util.List;


public interface MovimientoInventarioDetalleRepo extends JpaRepository<MovimientoInventarioDetalle, Integer> {
	List<MovimientoInventarioDetalle> findByMovimientoInventario(MovimientoInventario movimientoInventario);
}
