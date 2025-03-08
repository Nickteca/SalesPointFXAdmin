package com.salespointfxadmin.www;

import org.springframework.context.ConfigurableApplicationContext;

import com.salespointfxadmin.www.service.MovimientoCajaService;
import com.salespointfxadmin.www.service.SucursalService;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SalesPointFxAdminApplicationFx extends Application {

	private MovimientoCajaService mcs;
	private SucursalService ss;
	private ConfigurableApplicationContext context;

	@Override
	public void init() throws Exception {
		context = SalesPointFxAdminApplication.getContext();
		mcs = context.getBean(MovimientoCajaService.class);
		ss = context.getBean(SucursalService.class);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		if (ss.getSucursalActive() != null) {
			System.out.println("Hay sucursal activa");
			primaryStage.show();
		} else {
			System.out.println("No hay sucursal activa");
			primaryStage.show();
		}
	}

	@Override
	public void stop() throws Exception {
		context.close(); // Cerrar el contexto de Spring Boot cuando se cierre la aplicación JavaFX
	}

}
