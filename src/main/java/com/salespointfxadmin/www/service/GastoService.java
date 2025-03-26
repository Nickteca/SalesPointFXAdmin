package com.salespointfxadmin.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.Gasto;
import com.salespointfxadmin.www.repositorie.GastoRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GastoService {
	private final GastoRepo gr;

	public List<Gasto> findAll() {
		return gr.findAll();
	}

	public Gasto save(Gasto g) {
		return gr.save(g);
	}

}
