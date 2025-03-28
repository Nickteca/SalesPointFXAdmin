package com.salespointfxadmin.www.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.SucursalRecoleccion;
import com.salespointfxadmin.www.repositorie.SucursalRecoleccionRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalRecoleccionService {
	private final SucursalRecoleccionRepo srr;
	
	public List<SucursalRecoleccion> findBySucursalEstatusSucursalTrueAndCreatedAtBetween(LocalDate fi, LocalDate ff){
		return srr.findBySucursalEstatusSucursalTrueAndCreatedAtBetween(fi.atStartOfDay(), ff.atTime(23, 59, 59));
	}
}
