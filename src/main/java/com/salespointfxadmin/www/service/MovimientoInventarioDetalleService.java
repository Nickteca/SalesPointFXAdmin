package com.salespointfxadmin.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.MovimientoInventario;
import com.salespointfxadmin.www.model.MovimientoInventarioDetalle;
import com.salespointfxadmin.www.repositorie.MovimientoInventarioDetalleRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoInventarioDetalleService {
	private final MovimientoInventarioDetalleRepo midr;
	
	public List<MovimientoInventarioDetalle> findByMovimiento(MovimientoInventario mi){
		return midr.findByMovimientoInventario(mi);
	}
}
