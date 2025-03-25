package com.salespointfxadmin.www.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.enums.Naturaleza;
import com.salespointfxadmin.www.model.Folio;
import com.salespointfxadmin.www.model.MovimientoInventario;
import com.salespointfxadmin.www.model.MovimientoInventarioDetalle;
import com.salespointfxadmin.www.model.SucursalProducto;
import com.salespointfxadmin.www.repositorie.FolioRepo;
import com.salespointfxadmin.www.repositorie.MovimientoInventarioDetalleRepo;
import com.salespointfxadmin.www.repositorie.MovimientoInventarioRepo;
import com.salespointfxadmin.www.repositorie.SucursalProductoRepo;
import com.salespointfxadmin.www.repositorie.SucursalRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoInventarioService2 {
	private final MovimientoInventarioRepo mir;
	private final FolioRepo fr;
	private final SucursalProductoRepo spr;
	private final MovimientoInventarioDetalleRepo midr;
	private final SucursalRepo sr;

	@Transactional
	public MovimientoInventario save(MovimientoInventario mi, List<MovimientoInventarioDetalle> lmid, Folio f) {
		try {
			Optional<MovimientoInventario> existingMovimiento = mir.findByfolioCompuesto(mi.getFolioCompuesto());

			if (existingMovimiento.isEmpty()) {
				System.out.println("Entro al vancio");
				// **CASO: NUEVO MOVIMIENTO**
				mi = mir.save(mi);
				if (mi != null) {
					for (MovimientoInventarioDetalle mid : lmid) {
						mid.setMovimientoInventario(mi);
						actualizarInventario(mid, mi.getNaturaleza(), true);
						midr.save(mid);
					}

					// Incrementar folio solo si es nuevo
					f.setNumeroFolio(f.getNumeroFolio() + 1);
					fr.save(f);
				}

			} else {
				System.out.println("Entro al que si eciste");
				// **CASO: MODIFICACIÓN DEL MOVIMIENTO**
				MovimientoInventario miExistente = existingMovimiento.get();
				List<MovimientoInventarioDetalle> detallesAntiguos = miExistente.getListMovimientoInventarioDetalle();

				// 1. **Revertir el impacto de los detalles antiguos en el inventario**
				for (MovimientoInventarioDetalle midAntiguo : detallesAntiguos) {
					actualizarInventario(midAntiguo, miExistente.getNaturaleza(), false);
				}

				// 2. **Actualizar datos del movimiento**
				miExistente.setNaturaleza(mi.getNaturaleza());
				miExistente.setDecripcion(mi.getDecripcion());
				mir.save(miExistente);

				// 3. **Aplicar los nuevos detalles y actualizar el inventario**
				for (MovimientoInventarioDetalle midNuevo : lmid) {
					midNuevo.setMovimientoInventario(miExistente);
					actualizarInventario(midNuevo, miExistente.getNaturaleza(), true);
					midr.save(midNuevo);
				}
			}
		} catch (Exception e) {
			System.out.println("Error al guardar MovimientoInventario: " + e.getMessage());
			e.printStackTrace();
		}

		return mi;
	}

	private void actualizarInventario(MovimientoInventarioDetalle mid, Naturaleza naturaleza, boolean agregar) {
		Optional<SucursalProducto> osp = spr.findBySucursalEstatusSucursalTrueAndProducto(mid.getSucursalProducto().getProducto());

		SucursalProducto sp;
		if (osp.isPresent()) {
			sp = osp.get();
		} else {
			// **Caso: El producto aún no existe en la sucursal, se debe crear**
			sp = new SucursalProducto();
			sp.setSucursal(mid.getMovimientoInventario().getSucursal());
			sp.setProducto(mid.getSucursalProducto().getProducto());
			sp.setInventario(0); // Inicializar en 0
			spr.save(sp);
		}

		// **Actualizar inventario**
		float ajuste = agregar ? mid.getUnidades() : -mid.getUnidades();
		float nuevoInventario = naturaleza.equals(Naturaleza.E) ? sp.getInventario() + ajuste : sp.getInventario() - ajuste;

		sp.setInventario(nuevoInventario);
		spr.save(sp);
	}

}
