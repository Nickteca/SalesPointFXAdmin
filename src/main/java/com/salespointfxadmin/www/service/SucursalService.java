package com.salespointfxadmin.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.repositorie.SucursalRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalService {
	private final SucursalRepo sr;
	private final FolioService fs;

	public Sucursal getSucursalActive() {
		System.out.println(sr.findByEstatusSucursalTrue());
		return sr.findByEstatusSucursalTrue();
	}

	public List<Sucursal> getAllSucursales() {
		return sr.findAll();
	}

	@Transactional
	public Sucursal activarSucursal(Sucursal sucursal) {
		Sucursal s = sr.save(sucursal);
		fs.insertFolios(s);
		return s;
	}
}
