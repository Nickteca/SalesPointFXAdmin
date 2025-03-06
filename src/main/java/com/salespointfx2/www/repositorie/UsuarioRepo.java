package com.salespointfx2.www.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfx2.www.model.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Short> {

}
