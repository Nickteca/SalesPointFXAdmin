package com.salespointfxadmin.www.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.SucursalPedido;
import com.salespointfxadmin.www.repositorie.SucursalPedidoRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalPedidoService {
	private final SucursalPedidoRepo spr;
	private final SucursalService ss;

	public SucursalPedido save(SucursalPedido sp) {
		sp.setCreatedAt(LocalDateTime.now());
		sp.setSucursal(ss.getSucursalActive());
		return spr.save(sp);
	}
}
