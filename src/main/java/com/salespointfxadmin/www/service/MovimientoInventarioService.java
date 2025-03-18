package com.salespointfxadmin.www.service;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.repositorie.MovimientoInventarioRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoInventarioService {
	private final MovimientoInventarioRepo mir;

}
