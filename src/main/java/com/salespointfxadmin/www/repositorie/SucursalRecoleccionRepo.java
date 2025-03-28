package com.salespointfxadmin.www.repositorie;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.SucursalRecoleccion;

public interface SucursalRecoleccionRepo extends JpaRepository<SucursalRecoleccion, Integer> {
	List<SucursalRecoleccion> findBySucursalEstatusSucursalTrueAndCreatedAtBetween(LocalDateTime inicio, LocalDateTime fin);
}
