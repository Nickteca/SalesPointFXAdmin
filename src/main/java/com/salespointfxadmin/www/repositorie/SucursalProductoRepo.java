package com.salespointfxadmin.www.repositorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.model.SucursalProducto;

public interface SucursalProductoRepo extends JpaRepository<SucursalProducto, Short> {
	List<SucursalProducto> findBySucursal(Sucursal sucursal);
}
