package com.salespointfxadmin.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SalesPointFxAdminApplication {
	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(SalesPointFxAdminApplication.class, args);
		SalesPointFxAdminApplicationFx.launch(SalesPointFxAdminApplicationFx.class, args);
	}

	public static ConfigurableApplicationContext getContext() {
		return context;
	}

}