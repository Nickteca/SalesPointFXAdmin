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
				// **CASO: NUEVO MOVIMIENTO**
				mi = mir.save(mi);
				if (mi != null) {
					for (MovimientoInventarioDetalle mid : lmid) {
						Optional<SucursalProducto> osp = spr.findBySucursalAndProducto(mi.getSucursal(), mid.getSucursalProducto().getProducto());
						if (osp.isPresent()) {
							SucursalProducto sp = osp.get();
							if (mi.getNaturaleza().equals(Naturaleza.Entrada)) {
								sp.setInventario(sp.getInventario() + mid.getUnidades());
							} else {
								sp.setInventario(sp.getInventario() - mid.getUnidades());
							}
							spr.save(sp);
						}
						mid.setMovimientoInventario(mi);
						midr.save(mid);
					}

					// Incrementar folio solo si es nuevo
					f.setNumeroFolio(f.getNumeroFolio() + 1);
					fr.save(f);
				}

			} else {
				List<MovimientoInventarioDetalle> lmide = midr.findByMovimientoInventario(mi);
				for (MovimientoInventarioDetalle mid : lmid) {
					if (mid.getIdMovimientoInventarioDetalle() == null) {
						Optional<SucursalProducto> osp = spr.findBySucursalEstatusSucursalTrueAndProducto(mid.getSucursalProducto().getProducto());
						if (osp.isPresent()) {
							SucursalProducto sp = osp.get();
							if (mi.getNaturaleza().equals(Naturaleza.Entrada)) {
								sp.setInventario(sp.getInventario() + mid.getUnidades());
							} else {
								sp.setInventario(sp.getInventario() - mid.getUnidades());
							}
							spr.save(sp);
						}
					} else {
						MovimientoInventarioDetalle antiguoIngreso = lmide.stream().filter(det -> det.getIdMovimientoInventarioDetalle().equals(mid.getIdMovimientoInventarioDetalle())).findFirst()
								.orElse(null);
						if (antiguoIngreso != null) {
							float cantidadAnterior = antiguoIngreso.getUnidades(); // Obtener la cantidad anterior
							float diferencia = mid.getUnidades() - cantidadAnterior; // Diferencia entre nueva y anterior

							Optional<SucursalProducto> osp = spr.findBySucursalEstatusSucursalTrueAndProducto(mid.getSucursalProducto().getProducto());
							if (osp.isPresent()) {
								SucursalProducto sp = osp.get();

								// Ajustar inventario correctamente con la diferencia
								if (mi.getNaturaleza().equals(Naturaleza.Entrada)) {
									sp.setInventario(sp.getInventario() + diferencia);
								} else {
									sp.setInventario(sp.getInventario() - diferencia);
								}

								spr.save(sp);
							}
						}

					}
					mid.setMovimientoInventario(mi);
					midr.save(mid);
				}

			}
		} catch (Exception e) {
			System.out.println("Error al guardar MovimientoInventario: " + e.getMessage());
			e.printStackTrace();
		}

		return mi;
	}

	private void actualizarInventario(MovimientoInventarioDetalle mid, Naturaleza naturaleza, boolean esNuevo) {
		Optional<SucursalProducto> osp = spr.findBySucursalEstatusSucursalTrueAndProducto(mid.getSucursalProducto().getProducto());

		SucursalProducto sp;
		if (osp.isPresent()) {
			sp = osp.get();
		} else {
			// **Si el producto no existe en la sucursal, se crea**
			sp = new SucursalProducto();
			sp.setSucursal(mid.getMovimientoInventario().getSucursal());
			sp.setProducto(mid.getSucursalProducto().getProducto());
			sp.setInventario(0); // Se inicializa en 0
			spr.save(sp);
		}

		float cantidadNueva = mid.getUnidades();

		if (!esNuevo) {
			// **Si se está modificando, primero revertimos la cantidad anterior**
			float cantidadAnterior = mid.getUnidades(); // Necesitas agregar este campo en tu DTO o entidad

			if (naturaleza.equals(Naturaleza.Entrada)) {
				sp.setInventario(sp.getInventario() - cantidadAnterior);
			} else {
				sp.setInventario(sp.getInventario() + cantidadAnterior);
			}
		}

		// **Aplicamos la cantidad nueva**
		if (naturaleza.equals(Naturaleza.Entrada)) {
			sp.setInventario(sp.getInventario() + cantidadNueva);
		} else {
			sp.setInventario(sp.getInventario() - cantidadNueva);
		}

		spr.save(sp);
	}

}
