package com.salespointfxadmin.www.component;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;

@Component
public class SpringFXMLLoader {
	private final ApplicationContext context;

	public SpringFXMLLoader(ApplicationContext context) {
		this.context = context;
	}

	public FXMLLoader load(String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
		loader.setControllerFactory(context::getBean); // Usar Spring para inyección
		return loader;
	}
}