package com.salespointfx2.www.component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.salespointfx2.www.model.Categoria;
import com.salespointfx2.www.model.Empresa;
import com.salespointfx2.www.model.Producto;
import com.salespointfx2.www.model.Sucursal;
import com.salespointfx2.www.model.Usuario;
import com.salespointfx2.www.model.UsuarioRol;
import com.salespointfx2.www.repositorie.CategoriaRepo;
import com.salespointfx2.www.repositorie.EmpresaRepo;
import com.salespointfx2.www.repositorie.ProductoRepo;
import com.salespointfx2.www.repositorie.RolRepo;
import com.salespointfx2.www.repositorie.SucursalRepo;
import com.salespointfx2.www.repositorie.UsuarioRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitialed implements CommandLineRunner {
	private final EmpresaRepo er;
	private final SucursalRepo sr;
	private final RolRepo rr;
	private final UsuarioRepo ur;
	private final CategoriaRepo cr;
	private final ProductoRepo pr;

	@Override
	public void run(String... args) throws Exception {
		saveSucursal(saveEmpresa());
		insertarCategorias();
		insertarRoles();
		insertarUsuarios();
	}

	private Empresa saveEmpresa() {
		if (er.count() == 0) {
			return er.save(new Empresa(null, "EL REY DEL POLLO", "Libramiento Ignazio zaragoz 406, patzcuaro michoacan", "4341109817", "LUAI890602TP0", LocalDateTime.now()));
		} else {
			return null;
		}
	}

	private void saveSucursal(Empresa empresa) {
		if (sr.count() == 0) {
			sr.save(new Sucursal(null, "PATZCUARO", "RAMOS 15 COLONIA CENTRO, CP 61800", "PATZCUARO", "MICHOACAN", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "TARIACURI", "RAMOS 15 COLONIA CENTRO, CP 61800", "PATZCUARO", "MICHOACAN", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "ESTACION", "RAMOS 15 COLONIA CENTRO, CP 61800", "PATZCUARO", "MICHOACAN", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "MORELIA", "RAMOS 15 COLONIA CENTRO, CP 61800", "MORELIA", "MICHOACAN", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "URIANGATO", "RAMOS 15 COLONIA CENTRO, CP 61800", "URIANGATO", "GUANAJUATO", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "SANTA ANA", "RAMOS 15 COLONIA CENTRO, CP 61800", "SANTA ANA", "GUANAJUATO", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "TZINTZUNZAN", "RAMOS 15 COLONIA CENTRO, CP 61800", "TZINTZUZAN", "MICHOACAN", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "QUIROGA", "RAMOS 15 COLONIA CENTRO, CP 61800", "QUIROGA", "MICHOACAN", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "ZACAPU", "RAMOS 15 COLONIA CENTRO, CP 61800", "ZACAPU", "MICHOACAN", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "ZIRACUA", "RAMOS 15 COLONIA CENTRO, CP 61800", "ZIRACUA", "MICHOACAN", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "TARETAN", "RAMOS 15 COLONIA CENTRO, CP 61800", "TARETAN", "MICHOACAN", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "LOMBARDIA", "RAMOS 15 COLONIA CENTRO, CP 61800", "LOMBARDIA", "MICHOACAN", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "NUEVA ITALIA", "RAMOS 15 COLONIA CENTRO, CP 61800", "NUEVA ITALIA", "MICHOACAN", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "APATZINGAN", "RAMOS 15 COLONIA CENTRO, CP 61800", "APATZINGAN", "MICHOACAN", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "GLORIETA", "RAMOS 15 COLONIA CENTRO, CP 61800", "APATZINGAN", "MICHOACAN", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "BULEBARD", "RAMOS 15 COLONIA CENTRO, CP 61800", "BULEBARD", "MICHOACAN", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "TINGAMBATO", "RAMOS 15 COLONIA CENTRO, CP 61800", "TINGAMBATO", "MICHOACAN", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "CERESO", "RAMOS 15 COLONIA CENTRO, CP 61800", "CERESO", "MICHOACAN", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "REGIONAL", "RAMOS 15 COLONIA CENTRO, CP 61800", "REGIONAL", "MICHOACAN", "1234567890", false, empresa));
			sr.save(new Sucursal(null, "JICALAN", "RAMOS 15 COLONIA CENTRO, CP 61800", "JICALAN", "MICHOACAN", "1234567890", false, empresa));
		}
	}

	private void insertarCategorias() {
		if (cr.count() == 0) {
			cr.save(new Categoria("ALIMENTOS"));
			cr.save(new Categoria("PAQUETES"));
			cr.save(new Categoria("BOTANAS"));
			cr.save(new Categoria("EXTRAS"));
			cr.save(new Categoria("BEBIDAS"));
		}
	}

	private List<UsuarioRol> insertarRoles() {
		List<UsuarioRol> lr = new ArrayList<>();
		if (rr.count() == 0) {
			lr.add(rr.save(new UsuarioRol("ADMIN")));
			lr.add(rr.save(new UsuarioRol("SUPER")));
			lr.add(rr.save(new UsuarioRol("USER")));
		} else {
			lr = rr.findAll();
		}
		return lr;
	}

	private void insertarUsuarios() {
		if (ur.count() == 0) {
			ur.save(new Usuario("admin", new BCryptPasswordEncoder().encode("admin"), rr.findByNombreRol("ADMIN")));
			ur.save(new Usuario("super", new BCryptPasswordEncoder().encode("super"), rr.findByNombreRol("SUPER")));
			ur.save(new Usuario("user", new BCryptPasswordEncoder().encode("user"), rr.findByNombreRol("USER")));
		}
	}

	private void insertarProductos() {
		if (pr.count() == 0) {
			pr.save(new Producto(null, "Pollo", false));
			pr.save(new Producto(null, "Costilla", false));
			pr.save(new Producto(null, "Pierna", false));
			pr.save(new Producto(null, "Pescuezos", false));
			pr.save(new Producto(null, "Salchicha", false));
			pr.save(new Producto(null, "Arroz", false));
			pr.save(new Producto(null, "Frijol", false));
			pr.save(new Producto(null, "Espagueti", false));
			pr.save(new Producto(null, "Salsa Extra", false));
			pr.save(new Producto(null, "Salsa Aceite", false));
			pr.save(new Producto(null, "Papas Fritas", false));
			pr.save(new Producto(null, "Salsa Casera", false));
			pr.save(new Producto(null, "Arroz 1/4", false));
			pr.save(new Producto(null, "Frijol 1/4", false));
			pr.save(new Producto(null, "Tortilla", false));
			pr.save(new Producto(null, "Refresco", false));
			pr.save(new Producto(null, "Cerveza", false));
			pr.save(new Producto(null, "Jugo", false));
			pr.save(new Producto(null, "Agua", false));
		}
	}

}
