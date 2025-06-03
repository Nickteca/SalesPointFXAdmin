package com.salespointfxadmin.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.Configuracion;
import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.repositorie.ConfiguracionRepo;
import com.salespointfxadmin.www.repositorie.SucursalRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalService {
	private final SucursalRepo sr;
	private final FolioService fs;
	private final ConfiguracionRepo cr;

	private final SucursalProductoService sps;

	public Sucursal getSucursalActive() {
		return sr.findByEstatusSucursalTrue();
	}

	public List<Sucursal> getAllSucursales() {
		return sr.findAll();
	}

	@Transactional
	public Sucursal activarSucursal(Sucursal sucursal) {
		Sucursal s = sr.save(sucursal);
		fs.insertFolios(s);
		sps.insertarSucursalProductos(sucursal);
		if (cr.count() == 0) {
			cr.save(new Configuracion(null, "puerto_bascula", "COM5", "Puerto com de la bascula", sr.findByEstatusSucursalTrue()));
			cr.save(new Configuracion(null, "impresora_ticket", "XP-80C", "Impresora que se conectar", sr.findByEstatusSucursalTrue()));
			cr.save(new Configuracion(null, "correo_corte", "isaaclunaavila@gmail.com", "correo electronico para el corte", sr.findByEstatusSucursalTrue()));
		}
		return s;
	}
}
