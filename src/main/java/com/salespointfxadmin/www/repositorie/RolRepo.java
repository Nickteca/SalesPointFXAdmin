package com.salespointfxadmin.www.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.UsuarioRol;

public interface RolRepo extends JpaRepository<UsuarioRol, Short> {
	UsuarioRol findByNombreRol(String nombreRol);
}
