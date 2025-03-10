package com.salespointfxadmin.www.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.Sucursal;

public interface SucursalRepo extends JpaRepository<Sucursal, Short> {
	Sucursal findByEstatusSucursalTrue();
}
