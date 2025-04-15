package com.salespointfxadmin.www.repositorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.enums.BilleteValor;
import com.salespointfxadmin.www.model.Billete;
import com.salespointfxadmin.www.model.SucursalRecoleccion;

public interface BilleteRepo extends JpaRepository<Billete, Integer> {
	//List<Billete> findBySucursalRecoleccion(SucursalRecoleccion sucursalRecoleccion);
	Billete  findByBilleteValor(BilleteValor billeteValor);
}
