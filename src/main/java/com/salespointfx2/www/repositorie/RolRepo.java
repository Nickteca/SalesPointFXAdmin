package com.salespointfx2.www.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfx2.www.model.UsuarioRol;

public interface RolRepo extends JpaRepository<UsuarioRol, Short> {
	UsuarioRol findByNombreRol(String nombreRol);
}
