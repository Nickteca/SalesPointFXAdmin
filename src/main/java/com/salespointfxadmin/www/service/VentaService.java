package com.salespointfxadmin.www.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salespointfxadmin.www.model.ProductoPaquete;
import com.salespointfxadmin.www.model.SucursalProducto;
import com.salespointfxadmin.www.model.Venta;
import com.salespointfxadmin.www.model.VentaDetalle;
import com.salespointfxadmin.www.repositorie.ProductoPaqueteRepo;
import com.salespointfxadmin.www.repositorie.SucursalProductoRepo;
import com.salespointfxadmin.www.repositorie.VentaRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaService {
	private final MovimientoInventarioService2 mis;
	private final VentaRepo vr;
	private final ProductoPaqueteRepo ppr;
	private final SucursalProductoRepo spr;

	@Transactional
	public Venta cancelarVenta(String folio) {
		try {
			Venta v = vr.findByFolio(folio);
			if (v == null) {
				throw new IllegalArgumentException("Venta no encontrada");
			}
			if (v.isStatus() == false) {
				throw new IllegalArgumentException("Venta ya esta ancelada");
			}

			v.setStatus(false);
			v.setUpdatedAt(LocalDateTime.now());
			devolverInventarioPorCancelacion(v);
			v = vr.save(v);
			if (mis.ventaCancelada(v) != null) {
				return v;
			} else {
				throw new IllegalArgumentException("No se inserto movimiento");
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void devolverInventarioPorCancelacion(Venta v) {
		try {
			// Acumular los descuentos por cada producto-sucursal
			Map<Short, Float> descuentos = new HashMap<>();

			for (VentaDetalle vd : v.getListVentaDetalle()) {
				SucursalProducto sp = vd.getSucursalProducto();
				if (sp.getProducto().isEsPaquete()) {
					List<ProductoPaquete> lpp = ppr.findByPaquete(sp.getProducto());

					for (ProductoPaquete pp : lpp) {
						SucursalProducto spa = spr.findByProductoAndSucursal(pp.getProductoPaquete(), v.getSucursal()).orElseThrow(() -> new RuntimeException("No se encontró producto en sucursal"));

						short idSp = spa.getIdSucursalProducto();
						float cantidadADescontar = (pp.getCantidad() * vd.getCantidad());

						descuentos.merge(idSp, cantidadADescontar, Float::sum);
					}
				} else {
					short idSp = sp.getIdSucursalProducto();
					descuentos.merge(idSp, (float) vd.getCantidad(), Float::sum);
				}
			}

			// Aplicar descuentos ya acumulados
			for (Map.Entry<Short, Float> entry : descuentos.entrySet()) {
				SucursalProducto sp = spr.findById(entry.getKey()).orElseThrow();
				sp.setInventario(sp.getInventario() + entry.getValue());
				spr.save(sp);
			}

		} catch (Exception e) {
			System.err.println("Error al actualizar inventario: " + e.getMessage());
			throw e;
		}
	}
}
