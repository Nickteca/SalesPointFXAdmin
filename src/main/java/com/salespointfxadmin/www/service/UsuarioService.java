package com.salespointfxadmin.www.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.Usuario;
import com.salespointfxadmin.www.repositorie.UsuarioRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
	private final UsuarioRepo ur;
	private BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
	
	public Usuario findByusuario(String usuario,String password) {
		Usuario u = ur.findByNombreUsuario(usuario);
		if(u != null && bcpe.matches(password,u.getPasswordUsuario())) {
			return u;
		}
		else {
			return null;
		}
	}
}
