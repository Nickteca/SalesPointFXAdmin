package com.salespointfxadmin.www.service;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.repositorie.MovimientoInventarioDetalleRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoInventarioDetalleService {
	private final MovimientoInventarioDetalleRepo midr;
}
