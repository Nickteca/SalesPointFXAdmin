package com.salespointfxadmin.www.repositorie;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.Producto;
import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.model.SucursalProducto;

public interface SucursalProductoRepo extends JpaRepository<SucursalProducto, Short> {
	List<SucursalProducto> findBySucursalEstatusSucursalTrueAndProductoEsPaqueteFalse();

	List<SucursalProducto> findBySucursalAndVendibleTrue(Sucursal sucursal);

	List<SucursalProducto> findBySucursalAndProductoEsPaqueteTrue(Sucursal sucursal);

	SucursalProducto findBySucursalAndProductoNombreProducto(Sucursal sucursal, String nombreProducto);

	Optional<SucursalProducto> findBySucursalAndProducto(Sucursal sucursal, Producto producto);

	Optional<SucursalProducto> findBySucursalEstatusSucursalTrueAndProducto(Producto producto);

	SucursalProducto findBySucursalEstatusSucursalTrueAndIdSucursalProducto(Short idSucursalProducto);

	Optional<SucursalProducto> findByProductoAndSucursal(Producto p, Sucursal s);
}
