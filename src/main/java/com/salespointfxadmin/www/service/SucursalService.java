package com.salespointfxadmin.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.repositorie.SucursalRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalService {
	private final SucursalRepo sr;

	public Sucursal getSucursalActive() {
		return sr.findByEstatusSucursalIsTrue();
	}

	public List<Sucursal> getAllSucursales() {
		return sr.findAll();
	}
}
