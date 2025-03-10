package com.salespointfxadmin.www.service;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.MovimientoCaja;
import com.salespointfxadmin.www.repositorie.MovimientoCajaRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoCajaService {
	private final MovimientoCajaRepo mcr;
	private final SucursalService ss;

	public MovimientoCaja getLastMovmientoCaja() {
		return mcr.findFirstBySucursalOrderByIdMovimientoCajaDesc(ss.getSucursalActive());
	}
}
