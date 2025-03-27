package com.salespointfxadmin.www.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.SucursalGasto;
import com.salespointfxadmin.www.repositorie.SucursalGastoRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalGastoService {
	private final SucursalGastoRepo sgr;
	private final SucursalService ss;

	public SucursalGasto save(SucursalGasto sg) {
		sg.setSucursal(ss.getSucursalActive());
		return sgr.save(sg);
	}

	public List<SucursalGasto> findByCreatedAt(LocalDate inicio, LocalDate fin) {
		return sgr.findBySucursalEstatusSucursalTrueAndCreatedAtBetween(inicio.atStartOfDay(), fin.atTime(23, 59, 59));
	}
}
