package com.salespointfxadmin.www.repositorie;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.SucursalPedido;

public interface SucursalPedidoRepo extends JpaRepository<SucursalPedido, Integer> {
	List<SucursalPedido>findBySucursalEstatusSucursalTrueAndCreatedAtBetween(LocalDateTime inicio, LocalDateTime fin);
}
