package com.salespointfxadmin.www.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.SucursalPedido;
import com.salespointfxadmin.www.model.SucursalPedidoDetalle;
import java.util.List;


public interface SucursalPedidoDetalleRepo extends JpaRepository<SucursalPedidoDetalle, Integer> {
	List<SucursalPedidoDetalle> findBySucursalPedido(SucursalPedido sucursalPedido);
}
