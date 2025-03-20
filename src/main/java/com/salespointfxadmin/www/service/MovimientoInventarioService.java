package com.salespointfxadmin.www.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.enums.NombreFolio;
import com.salespointfxadmin.www.model.MovimientoCaja;
import com.salespointfxadmin.www.model.MovimientoCaja.TipoMovimiento;
import com.salespointfxadmin.www.model.MovimientoInventario;
import com.salespointfxadmin.www.model.MovimientoInventarioDetalle;
import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.repositorie.MovimientoCajaRepo;
import com.salespointfxadmin.www.repositorie.MovimientoInventarioDetalleRepo;
import com.salespointfxadmin.www.repositorie.MovimientoInventarioRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoInventarioService {
	private final MovimientoInventarioRepo mir;
	private final MovimientoInventarioDetalleRepo midr;
	private final MovimientoCajaRepo mcr;

	@Transactional
	public MovimientoInventario save(MovimientoInventario mi, List<MovimientoInventarioDetalle> mid) {
		MovimientoCaja mc = mcr.findFirstBySucursalEstatusSucursalTrueOrderByIdMovimientoCajaDesc().orElse(null);
		if (mc != null && mc.getTipoMovimientoCaja().equals(TipoMovimiento.APERTURA)) {
			mi = mir.save(mi);
			for (MovimientoInventarioDetalle movimientoInventarioDetalle : mid) {
				movimientoInventarioDetalle.setMovimientoInventario(mi);
				midr.save(movimientoInventarioDetalle);
			}
			return mi;
		} else {
			return null;
		}

	}

	public List<MovimientoInventario> findBySucursalAndCreatedAtBetweenAndNombreFolio(Sucursal sucursal, LocalDateTime startTime, LocalDateTime endTime, NombreFolio nombreFolio) {
		return mir.findBySucursalAndCreatedAtBetweenAndNombreFolio(sucursal, startTime, endTime, nombreFolio);
	}
}
