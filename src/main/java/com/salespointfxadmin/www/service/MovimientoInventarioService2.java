package com.salespointfxadmin.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.MovimientoInventario;
import com.salespointfxadmin.www.model.MovimientoInventarioDetalle;
import com.salespointfxadmin.www.repositorie.FolioRepo;
import com.salespointfxadmin.www.repositorie.MovimientoInventarioRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoInventarioService2 {
	private final MovimientoInventarioRepo mir;
	private final FolioRepo fr;
	
	public MovimientoInventario save(MovimientoInventario mi, List<MovimientoInventarioDetalle> lmid) {
		if(fr.findByNombreFolioAndSucursalEstatusSucursalTrue(mi.getNombreFolio()).isEmpty()) {
			
		}
	}
}
