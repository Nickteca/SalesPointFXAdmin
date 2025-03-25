package com.salespointfxadmin.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.enums.Naturaleza;
import com.salespointfxadmin.www.enums.NombreFolio;
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
		fr.save(new Folio("VEN-", 1, Naturaleza.S, sucursal, NombreFolio.Venta));
		fr.save(new Folio("ENT-", 1, Naturaleza.E, sucursal, NombreFolio.Ajuste_Entrada));
		fr.save(new Folio("SAL-", 1, Naturaleza.S, sucursal, NombreFolio.Ajuste_salida));
		fr.save(new Folio("TRS-", 1, Naturaleza.S, sucursal, NombreFolio.Trspaso_Salida));
		fr.save(new Folio("TRE-", 1, Naturaleza.E, sucursal, NombreFolio.Traspaso_Entrada));
		fr.save(new Folio("DEV-", 1, Naturaleza.S, sucursal, NombreFolio.Devolucion_Venta));
		fr.save(new Folio("CAN-", 1, Naturaleza.E, sucursal, NombreFolio.Cancelacion_Venta));
	}

	public List<Folio> findBySucursalEstatusSucursalTrue() {
		return fr.findBySucursalEstatusSucursalTrue();
	}

	public Folio findByFolioSucursalEstatisTrue(NombreFolio nf) {
		return fr.findByNombreFolioAndSucursalEstatusSucursalTrue(nf).get();
	}
}
