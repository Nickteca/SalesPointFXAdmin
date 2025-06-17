package com.salespointfxadmin.www.component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.enums.BilleteValor;
import com.salespointfxadmin.www.model.Billete;
import com.salespointfxadmin.www.model.Categoria;
import com.salespointfxadmin.www.model.Configuracion;
import com.salespointfxadmin.www.model.Empresa;
import com.salespointfxadmin.www.model.Gasto;
import com.salespointfxadmin.www.model.Producto;
import com.salespointfxadmin.www.model.ProductoPaquete;
import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.model.Usuario;
import com.salespointfxadmin.www.model.UsuarioRol;
import com.salespointfxadmin.www.repositorie.BilleteRepo;
import com.salespointfxadmin.www.repositorie.CategoriaRepo;
import com.salespointfxadmin.www.repositorie.ConfiguracionRepo;
import com.salespointfxadmin.www.repositorie.EmpresaRepo;
import com.salespointfxadmin.www.repositorie.GastoRepo;
import com.salespointfxadmin.www.repositorie.ProductoPaqueteRepo;
import com.salespointfxadmin.www.repositorie.ProductoRepo;
import com.salespointfxadmin.www.repositorie.RolRepo;
import com.salespointfxadmin.www.repositorie.SucursalRepo;
import com.salespointfxadmin.www.repositorie.UsuarioRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component

@Slf4j
@RequiredArgsConstructor
public class DataInitialed implements CommandLineRunner {
	private final EmpresaRepo er;
	private final SucursalRepo sr;
	private final RolRepo rr;
	private final UsuarioRepo ur;
	private final CategoriaRepo cr;
	private final ProductoRepo pr;
	private final GastoRepo gr;
	private final ProductoPaqueteRepo ppr;
	private final BilleteRepo br;
	private final ConfiguracionRepo configr;

	@Override
	public void run(String... args) throws Exception {
		saveSucursal(saveEmpresa());
		insertarCategorias();
		insertarRoles();
		insertarUsuarios();
		insertarProductos();
		insertarGastos();
		insertarPaquetes();
		insertarBillete();
		configuracion();
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
			sr.save(new Sucursal(null, "BODEGA", "RAMOS 15 COLONIA CENTRO, CP 61800", "PATZCUARO", "MICHOACAN", "1234567890", false, empresa));
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
			cr.save(new Categoria("PEDIDO"));
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
			pr.save(new Producto(null, "Papas Extra", false));
			pr.save(new Producto(null, "Salsa Mexicana", false));
			pr.save(new Producto(null, "Salsa Arbol", false));
			pr.save(new Producto(null, "Salsa Manzano", false));
			pr.save(new Producto(null, "Salsa Ranchera", false));
			pr.save(new Producto(null, "Arroz 1/4", false));
			pr.save(new Producto(null, "Frijol 1/4", false));
			pr.save(new Producto(null, "Tortilla", false));
			pr.save(new Producto(null, "Refresco", false));
			pr.save(new Producto(null, "Cerveza", false));
			pr.save(new Producto(null, "Jugo", false));
			pr.save(new Producto(null, "Agua", false));
			pr.save(new Producto(null, "Bulto papa", false));
			pr.save(new Producto(null, "Salsa don Vazco", false));
			pr.save(new Producto(null, "Adobo 1kg", false));
			pr.save(new Producto(null, "Especie 1kg", false));
			pr.save(new Producto(null, "Sal 1kg", false));
			pr.save(new Producto(null, "Bolsa Rollo p/Pollo", false));
			pr.save(new Producto(null, "Bolsa Rollo p/Salsa", false));
			pr.save(new Producto(null, "Bolsa de asa", false));
			pr.save(new Producto(null, "Bolas para basura", false));
			pr.save(new Producto(null, "Jabon 1kg", false));
			pr.save(new Producto(null, "Cloro 1kg", false));
			pr.save(new Producto(null, "Pinol 1kg", false));
			pr.save(new Producto(null, "Servilletas p/mesa", false));
			pr.save(new Producto(null, "Papel higienico", false));
			pr.save(new Producto(null, "Rollo para mano", false));
			pr.save(new Producto(null, "Fibras", false));
			pr.save(new Producto(null, "Escobas", false));
			pr.save(new Producto(null, "Jaladores", false));
			pr.save(new Producto(null, "Trapeadores", false));
			pr.save(new Producto(null, "Tenazas chicas", false));
			pr.save(new Producto(null, "Tenazas grandes", false));
			pr.save(new Producto(null, "Rollo impreora", false));
			pr.save(new Producto(null, "Focos", false));
			pr.save(new Producto(null, "Block de controles", false));
			pr.save(new Producto(null, "Bandeja/jicara", false));
			pr.save(new Producto(null, "Recohedor", false));
			pr.save(new Producto(null, "Pegamoscas", false));
			pr.save(new Producto(null, "Sasa", false));
			pr.save(new Producto(null, "Palillos", false));
			pr.save(new Producto(null, "Tijeras", false));
			pr.save(new Producto(null, "Tina para papas", false));
			pr.save(new Producto(null, "Coladeras con mango", false));
			pr.save(new Producto(null, "Parrila", false));
			pr.save(new Producto(null, "Franelas", false));
			pr.save(new Producto(null, "Cuchillos", false));
			pr.save(new Producto(null, "Pocillo", false));
			pr.save(new Producto(null, "Ganchos", false));
			pr.save(new Producto(null, "Bote de basura", false));
			pr.save(new Producto(null, "Liquido Edy", false));
			pr.save(new Producto(null, "Jbon liquido p/manos", false));
			pr.save(new Producto(null, "Atomizador", false));
			pr.save(new Producto(null, "Afloj todo DW-40", false));
			pr.save(new Producto(null, "Caja naranja", false));
			pr.save(new Producto(null, "Cesto papel de baño", false));
			pr.save(new Producto(null, "Canastillas", false));
			pr.save(new Producto(null, "Gel antibactereal", false));
			pr.save(new Producto(null, "Bolsa Rollo pescuezo", false));
			pr.save(new Producto(null, "Mandil tela", false));
			pr.save(new Producto(null, "Mandil plastico", false));
			pr.save(new Producto(null, "Playera chica", false));
			pr.save(new Producto(null, "Playera mediana", false));
			pr.save(new Producto(null, "Playera grande", false));
			pr.save(new Producto(null, "Playera extra grande", false));
			pr.save(new Producto(null, "Botas chicas", false));
			pr.save(new Producto(null, "Botas medianas", false));
			pr.save(new Producto(null, "Botas grandes", false));
			pr.save(new Producto(null, "kit medico", false));
			pr.save(new Producto(null, "Espatula", false));
			pr.save(new Producto(null, "Pala grande", false));
			pr.save(new Producto(null, "Pala chica", false));
			pr.save(new Producto(null, "Horno", false));
			pr.save(new Producto(null, "Refri", false));
			pr.save(new Producto(null, "Fibra Scotch", false));
		}
	}

	private void insertarPaquetes() {
		if (ppr.count() == 0) {
			Producto p = pr.save(new Producto(null, "1/2 pollo", true));
            ppr.save(new ProductoPaquete(null, p, 0.5f, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salchicha")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 pollo", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 1/2 pollo", true));
            ppr.save(new ProductoPaquete(null, p, 1.5f, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1/2 Costilla", true));
            ppr.save(new ProductoPaquete(null, p, 0.5f, pr.findByNombreProducto("Costilla")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salchicha")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 Costilla", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Costilla")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 1/2 Costilla", true));
            ppr.save(new ProductoPaquete(null, p, 1.5f, pr.findByNombreProducto("Costilla")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 Pierna", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Pierna")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 pollo 1/2 costilla", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 0.5f, pr.findByNombreProducto("Costilla")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 costilla 1/2 pollo", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Costilla")));
            ppr.save(new ProductoPaquete(null, p, 0.5f, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salsa Extra")));
            paquetesArmados();

		}
	}
	private void paquetesArmados(){
        try {
            /*1/2 POLLOS*/
            Producto p = pr.save(new Producto(null, "1/2 pollo +arropz 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 0.5f, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Arroz")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salchicha")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1/2 pollo +frijol 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 0.5f, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Frijol")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salchicha")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1/2 pollo +espagueti 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 0.5f, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Espagueti")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salchicha")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            /*POLLO ENTERO*/
            p = pr.save(new Producto(null, "1 pollo +arropz 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Arroz")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 pollo +frijol 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Frijol")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 pollo +espagueti 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Espagueti")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            /*1 1/2 POLLLO*/
            p = pr.save(new Producto(null, "1 1/2 pollo +arropz 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1.5f, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Arroz")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 1/2 pollo +frijol 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1.5f, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Frijol")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 1/2 pollo +espagueti 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1.5f, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Espagueti")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            /*1/2 COSTILLA*/
            p = pr.save(new Producto(null, "1/2 costilla +arropz 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 0.5f, pr.findByNombreProducto("Costilla")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Arroz")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salchicha")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1/2 costilla +frijol 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 0.5f, pr.findByNombreProducto("Costilla")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Frijol")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salchicha")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1/2 costilla +espagueti 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 0.5f, pr.findByNombreProducto("Costilla")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Espagueti")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salchicha")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            /*1 COSTILLA*/
            p = pr.save(new Producto(null, "1 Costilla +arropz 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Costilla")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Arroz")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 Costilla +frijol 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Costilla")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Frijol")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 Costilla +espagueti 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Costilla")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Espagueti")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            /*1 1/2 costilla*/
            p = pr.save(new Producto(null, "1 1/2 Costilla +arropz 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1.5f, pr.findByNombreProducto("Costilla")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Arroz")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 1/2 Costilla +frijol 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1.5f, pr.findByNombreProducto("Costilla")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Frijol")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 1/2 Costilla +espagueti 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1.5f, pr.findByNombreProducto("Costilla")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Espagueti")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            /*1 PIERNA*/
            p = pr.save(new Producto(null, "1 Pierna +arropz 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Pierna")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Arroz")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 Pierna +frijol 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Pierna")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Frijol")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 Pierna +espagueti 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Pierna")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Espagueti")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Salsa Extra")));

            /*1 POLO 1/2 COSTILLA*/
            p = pr.save(new Producto(null, "1 Pollo 1/2 costi +arroz 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 0.5f, pr.findByNombreProducto("costilla")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Arroz")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 Pollo 1/2 costi +frijol 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 0.5f, pr.findByNombreProducto("costilla")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Frijol")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 Pollo 1/2 costi +espagueti 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 0.5f, pr.findByNombreProducto("costilla")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Espagueti")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salsa Extra")));

            /*1 Costilla 1/2 Pollo*/
            p = pr.save(new Producto(null, "1 Costi 1/2 Pollo +arroz 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Costilla")));
            ppr.save(new ProductoPaquete(null, p, 0.5f, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Arroz")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 Costi 1/2 Pollo +frijol 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Costilla")));
            ppr.save(new ProductoPaquete(null, p, 0.5f, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Frijol")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "1 Costi 1/2 Pollo +espagueti 1/2", true));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Costilla")));
            ppr.save(new ProductoPaquete(null, p, 0.5f, pr.findByNombreProducto("Pollo")));
            ppr.save(new ProductoPaquete(null, p, 1, pr.findByNombreProducto("Espagueti")));
            ppr.save(new ProductoPaquete(null, p, 2, pr.findByNombreProducto("Salsa Extra")));

            p = pr.save(new Producto(null, "Pecuezos 6 X $30", true));
            ppr.save(new ProductoPaquete(null, p, 6, pr.findByNombreProducto("Pescuezos")));

            p = pr.save(new Producto(null, "Salchicha 5 X $20", true));
            ppr.save(new ProductoPaquete(null, p, 5, pr.findByNombreProducto("Salchicha")));
        } catch (Exception e) {
            log.error("Paquete saramados error: "+e.getMessage());
        }
    }

	private void insertarGastos() {
		if (gr.count() == 0) {
			gr.save(new Gasto(null, "Renta Local"));
			gr.save(new Gasto(null, "Pago Luz"));
			gr.save(new Gasto(null, "Pago de agua"));
			gr.save(new Gasto(null, "Pago de la leña"));
			gr.save(new Gasto(null, "Internet"));
			gr.save(new Gasto(null, "Salarios"));
			gr.save(new Gasto(null, "Herramienta"));
		}
	}

	private void insertarBillete() {
		if (br.count() == 0) {
			br.save(new Billete(null, BilleteValor.$1000));
			br.save(new Billete(null, BilleteValor.$500));
			br.save(new Billete(null, BilleteValor.$200));
			br.save(new Billete(null, BilleteValor.$100));
			br.save(new Billete(null, BilleteValor.$50));
			br.save(new Billete(null, BilleteValor.$20));
			br.save(new Billete(null, BilleteValor.$10));
			br.save(new Billete(null, BilleteValor.$5));
			br.save(new Billete(null, BilleteValor.$2));
			br.save(new Billete(null, BilleteValor.$1));
		}
	}

	private void configuracion() {
		try {
			if (configr.count() == 0) {
				configr.save(new Configuracion(null, "puerto_bascula", "COM5", "Puerto com de la bascula", sr.findByEstatusSucursalTrue()));
				configr.save(new Configuracion(null, "impresora_ticket", "XP-80C", "Impresora que se conectar", sr.findByEstatusSucursalTrue()));
				configr.save(new Configuracion(null, "correo_corte", "isaaclunaavila@gmail.com", "correo electronico para el corte", sr.findByEstatusSucursalTrue()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
