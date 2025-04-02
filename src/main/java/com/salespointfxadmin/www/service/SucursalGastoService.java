package com.salespointfxadmin.www.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.SucursalGasto;
import com.salespointfxadmin.www.repositorie.SucursalGastoRepo;
import com.salespointfxadmin.www.service.printer.ImprimirGasto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalGastoService {
	private final SucursalGastoRepo sgr;
	private final SucursalService ss;
	private final ImprimirGasto ig;

	public SucursalGasto save(SucursalGasto sg) {
		sg.setSucursal(ss.getSucursalActive());
		ig.imprimirGasto(sg);
		return sgr.save(sg);
	}

	public List<SucursalGasto> findBySucursalEstatusSucursalTrueAndCreatedAtBetween(LocalDate inicio, LocalDate fin) {
		return sgr.findBySucursalEstatusSucursalTrueAndCreatedAtBetween(inicio.atStartOfDay(), fin.atTime(23, 59, 59));
	}
	
	public void imprimir(SucursalGasto sg) {
		ig.imprimirGasto(sg);
	}
}
