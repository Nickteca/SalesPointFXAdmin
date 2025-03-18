package com.salespointfxadmin.www;

import java.io.IOException;
import java.net.ServerSocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

@SpringBootApplication
public class SalesPointFxAdminApplication extends Application {
	private static ConfigurableApplicationContext context;
	private static ServerSocket serverSocket; // Para mantener el puerto abierto

	public static void main(String[] args) {
		// Lanza la aplicación Spring Boot
		SpringApplication app = new SpringApplication(SalesPointFxAdminApplication.class);
		app.setWebApplicationType(org.springframework.boot.WebApplicationType.NONE); // Desactiva el servidor web
		context = app.run(args); // Inicia Spring Boot

		// Lanza JavaFX
		launch(args); // Inicia JavaFX después de que Spring Boot se haya iniciado
	}

	@Override
	public void init() throws Exception {
		// context = SpringApplication.run(SalesPointFxAdminApplication.class);
	}

	private static boolean isApplicationAlreadyRunning(int port) {
		try {
			serverSocket = new ServerSocket(port); // Intenta abrir el puerto
			serverSocket.setReuseAddress(true); // Permite reutilización rápida del puerto
			return false; // No está corriendo ya que se pudo abrir el puerto
		} catch (IOException e) {
			return true; // Ya está corriendo porque el puerto está ocupado
		}
	}

	public static ConfigurableApplicationContext getContext() {
		return context;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		if (!isApplicationAlreadyRunning(9999)) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
			loader.setControllerFactory(context::getBean); // Inyectar Spring Beans

			Parent root = loader.load();
			primaryStage.setTitle("Sistema de Ventas");
			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(false);
			primaryStage.show();
		} else {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Ya se ejecuto la palicacion");
			error.setHeaderText("Cierra esta intancia");
			error.setContentText("cerrar");
			error.showAndWait();
			System.exit(0);
		}
	}
}