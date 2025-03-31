package com.salespointfxadmin.www.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.Billete;
import com.salespointfxadmin.www.model.SucursalRecoleccion;
import com.salespointfxadmin.www.repositorie.SucursalRecoleccionRepo;
import com.salespointfxadmin.www.service.printer.CodigoBarrasYQR;
import com.salespointfxadmin.www.service.printer.ImprimirRecoleccion;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalRecoleccionService {
	private final SucursalRecoleccionRepo srr;
	private final SucursalService ss;
	private final ImprimirRecoleccion ir;

	private static CodigoBarrasYQR p;

	public List<SucursalRecoleccion> findBySucursalEstatusSucursalTrueAndCreatedAtBetween(LocalDate fi, LocalDate ff) {
		return srr.findBySucursalEstatusSucursalTrueAndCreatedAtBetween(fi.atStartOfDay(), ff.atTime(23, 59, 59));
	}

	@Transactional
	public SucursalRecoleccion save(SucursalRecoleccion sr, int total, List<Billete> lb) {
		sr.setCreatedAt(LocalDateTime.now());
		sr.setSucursal(ss.getSucursalActive());
		ir.imprimirRecoleccion(sr);
		return srr.save(sr);
	}
}
