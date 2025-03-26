package com.salespointfxadmin.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.MovimientoInventario;
import com.salespointfxadmin.www.model.MovimientoInventarioDetalle;
import com.salespointfxadmin.www.repositorie.MovimientoInventarioDetalleRepo;
import com.salespointfxadmin.www.repositorie.MovimientoInventarioRepo;
import com.salespointfxadmin.www.repositorie.SucursalProductoRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoInventarioDetalleService {
	private final MovimientoInventarioDetalleRepo midr;
	private final SucursalProductoRepo spr;

	public List<MovimientoInventarioDetalle> findByMovimiento(MovimientoInventario mi) {
		return midr.findByMovimientoInventario(mi);
	}

	public MovimientoInventarioDetalle save(MovimientoInventarioDetalle mid) {
		return midr.save(mid);
	}

	public void dalete(MovimientoInventarioDetalle mid) {
		float inventario = mid.getSucursalProducto().getInventario();
	}
}
