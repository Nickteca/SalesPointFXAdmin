package com.salespointfxadmin.www.service;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.repositorie.MovimientoCajaRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoCajaService {
	private MovimientoCajaRepo mcr;
	private SucursalService ss;

	public void getLastMovmientoCaja() {
	}
}
