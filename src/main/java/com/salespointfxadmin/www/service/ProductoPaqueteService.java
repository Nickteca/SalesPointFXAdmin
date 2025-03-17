package com.salespointfxadmin.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.ProductoPaquete;
import com.salespointfxadmin.www.repositorie.ProductoPaqueteRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoPaqueteService {
	private final ProductoPaqueteRepo ppr;

	public ProductoPaquete saveProductoPaqute(ProductoPaquete pp) {
		return ppr.save(pp);
	}

	public List<ProductoPaquete> saveAllProductoPaquete(List<ProductoPaquete> lpp) {
		return ppr.saveAll(lpp);
	}
}
