package com.salespointfxadmin.www.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.Usuario;


public interface UsuarioRepo extends JpaRepository<Usuario, Short> {
	Usuario findByNombreUsuario(String nombreUsuario);
}
