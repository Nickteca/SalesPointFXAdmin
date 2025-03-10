package com.salespointfxadmin.www.service;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.Folio;
import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.repositorie.FolioRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FolioService {
	private final FolioRepo fr;

	@Transactional
	public void insertFolios(Sucursal sucursal) {
		fr.save(new Folio("VEN-", 1, 'S', sucursal));
		fr.save(new Folio("ENT-", 1, 'E', sucursal));
		fr.save(new Folio("SAL-", 1, 'S', sucursal));
		fr.save(new Folio("TRS-", 1, 'S', sucursal));
		fr.save(new Folio("TRE-", 1, 'E', sucursal));
		fr.save(new Folio("DEV-", 1, 'E', sucursal));
		fr.save(new Folio("CAN-", 1, 'E', sucursal));
	}
}
