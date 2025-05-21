package com.salespointfxadmin.www.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.Configuracion;

public interface ConfiguracionRepo extends JpaRepository<Configuracion, Integer> {
	Configuracion findByClave(String clave);
}
