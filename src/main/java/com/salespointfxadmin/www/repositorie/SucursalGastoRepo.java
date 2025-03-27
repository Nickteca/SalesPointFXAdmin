package com.salespointfxadmin.www.repositorie;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.SucursalGasto;

public interface SucursalGastoRepo extends JpaRepository<SucursalGasto, Integer> {
	List<SucursalGasto> findBySucursalEstatusSucursalTrueAndCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
