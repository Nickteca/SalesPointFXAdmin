package com.salespointfxadmin.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.Configuracion;
import com.salespointfxadmin.www.repositorie.ConfiguracionRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfiguracionService {

	private final ConfiguracionRepo cr;

	public List<Configuracion> findAll() {
		try {
			return cr.findAll();
		} catch (Exception e) {
			throw e;
		}
	}

	public String getValor(String clave) {
		return cr.findByClave(clave).getValor();
	}

	public Configuracion save(Configuracion configuracion) {
		return cr.save(configuracion);
	}

	public void delete(Configuracion configuracion) {
		cr.delete(configuracion);
	}
}
