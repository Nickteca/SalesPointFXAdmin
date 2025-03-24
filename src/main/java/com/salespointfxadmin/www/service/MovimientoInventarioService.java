package com.salespointfxadmin.www.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.enums.Naturaleza;
import com.salespointfxadmin.www.enums.NombreFolio;
import com.salespointfxadmin.www.model.Folio;
import com.salespointfxadmin.www.model.MovimientoCaja;
import com.salespointfxadmin.www.model.MovimientoInventario;
import com.salespointfxadmin.www.model.MovimientoInventarioDetalle;
import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.model.SucursalProducto;
import com.salespointfxadmin.www.repositorie.FolioRepo;
import com.salespointfxadmin.www.repositorie.MovimientoCajaRepo;
import com.salespointfxadmin.www.repositorie.MovimientoInventarioRepo;
import com.salespointfxadmin.www.repositorie.SucursalProductoRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoInventarioService {
	private final MovimientoInventarioRepo mir;
	private final MovimientoCajaRepo mcr;
	private final SucursalProductoRepo spr;
	private final FolioRepo fr;

	@Transactional
	public MovimientoInventario save(MovimientoInventario mi, Folio f) {
		MovimientoCaja mc = mcr.findFirstBySucursalEstatusSucursalTrueOrderByIdMovimientoCajaDesc().orElse(null);
		// if (mc != null && mc.getTipoMovimientoCaja().equals(TipoMovimiento.APERTURA))
		// {

		mi = mir.save(mi);
		for (MovimientoInventarioDetalle mid : mi.getListMovimientoInventarioDetalle()) {
			Optional<SucursalProducto> osp = spr.findBySucursalAndProducto(mi.getSucursal(), mid.getSucursalProducto().getProducto());
			if (osp.isPresent()) {
				SucursalProducto sp = osp.get();
				if (mi.getNaturaleza().equals(Naturaleza.E)) {
					sp.setInventario(sp.getInventario() + mid.getUnidades());
				} else {
					sp.setInventario(sp.getInventario() - mid.getUnidades());
				}

				spr.save(sp);
			}
		}
		f.setNumeroFolio(f.getNumeroFolio() + 1);
		fr.save(f);
		return mi;
		// } else {
		// return null;
		// }
	}

	public List<MovimientoInventario> findBySucursalAndCreatedAtBetweenAndNombreFolio(Sucursal sucursal, LocalDateTime startTime, LocalDateTime endTime, NombreFolio nombreFolio) {
		return mir.findBySucursalAndCreatedAtBetweenAndNombreFolio(sucursal, startTime, endTime, nombreFolio);
	}
}
