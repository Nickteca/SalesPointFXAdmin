package com.salespointfxadmin.www.repositorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.enums.NombreFolio;
import com.salespointfxadmin.www.model.Folio;
import com.salespointfxadmin.www.model.Sucursal;

public interface FolioRepo extends JpaRepository<Folio, Short> {
	List<Folio> findBySucursal(Sucursal sucursal);

	Folio findByNombreFolioAndSucursalEstatusSucursalTrue(NombreFolio nf);
}
