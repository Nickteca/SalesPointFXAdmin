package com.salespointfxadmin.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.Categoria;
import com.salespointfxadmin.www.repositorie.CategoriaRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {
	private final CategoriaRepo cr;

	public List<Categoria> getAllCategorias() {
		return cr.findAll();
	}
}
