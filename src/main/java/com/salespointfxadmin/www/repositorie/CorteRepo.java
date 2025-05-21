package com.salespointfxadmin.www.repositorie;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.Corte;

public interface CorteRepo extends JpaRepository<Corte, Integer> {
	List<Corte> findByCierreBetweenAndSucursalEstatusSucursalTrue(LocalDateTime inicio, LocalDateTime fin);

	Corte findByIdCorte(Integer idCorte);
}
