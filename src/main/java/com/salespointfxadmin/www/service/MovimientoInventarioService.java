package com.salespointfxadmin.www.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.MovimientoInventario;
import com.salespointfxadmin.www.repositorie.MovimientoInventarioRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoInventarioService {
	private final MovimientoInventarioRepo mir;
	
	public List<MovimientoInventario> findByCreatedAtBetween(LocalDateTime startTime, LocalDateTime endTime){
		return mir.findByCreatedAtBetween(startTime.MIN, endTime.MAX);
	}
}
