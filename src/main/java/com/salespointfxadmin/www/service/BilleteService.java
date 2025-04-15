package com.salespointfxadmin.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.Billete;
import com.salespointfxadmin.www.model.SucursalRecoleccion;
import com.salespointfxadmin.www.repositorie.BilleteRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BilleteService {
	private final BilleteRepo br;

	/*public List<Billete> findBySucursalRecoleccion(SucursalRecoleccion sr) {
		return br.findBySucursalRecoleccion(sr);
	}*/
}
