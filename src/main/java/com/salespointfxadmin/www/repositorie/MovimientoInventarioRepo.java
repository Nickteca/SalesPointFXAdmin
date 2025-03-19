package com.salespointfxadmin.www.repositorie;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.MovimientoInventario;

public interface MovimientoInventarioRepo extends JpaRepository<MovimientoInventario, Integer> {
	List<MovimientoInventario> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
