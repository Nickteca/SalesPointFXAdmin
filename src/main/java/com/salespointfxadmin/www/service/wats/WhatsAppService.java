package com.salespointfxadmin.www.service.wats;

import java.io.IOException;

import org.springframework.stereotype.Service;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class WhatsAppService {
	private static final String INSTANCE_ID = "instance108464"; // Reemplaza con tu ID de UltraMsg
	private static final String TOKEN = "z9e4s2vvyuktm470"; // Reemplaza con tu Token de UltraMsg
	private static final String API_URL = "https://api.ultramsg.com/" + INSTANCE_ID + "/messages/chat";

	public void sendWhatsAppMessage(String numero, String mensaje, String pdfFilePath, String nmbreFile) throws IOException {
		/*
		 * OkHttpClient client = new OkHttpClient(); RequestBody body = new
		 * FormBody.Builder().add("token", TOKEN).add("to", numero).add("filename",
		 * nmbreFile).add("document", pdfFilePath).add("caption", "document caption")
		 * 
		 * .build();
		 * 
		 * Request request = new Request.Builder().url("https://api.ultramsg.com/" +
		 * INSTANCE_ID + "/messages/document").post(body).addHeader("content-type",
		 * "application/x-www-form-urlencoded").build();
		 * 
		 * Response response = client.newCall(request).execute();
		 * 
		 * System.out.println(response.body().string());
		 */

		OkHttpClient client = new OkHttpClient();
		RequestBody body = new FormBody.Builder().add("token", TOKEN).add("to", numero).add("body", mensaje)

				.build();

		Request request = new Request.Builder().url("https://api.ultramsg.com/instance108464/messages/chat").post(body).addHeader("content-type", "application/x-www-form-urlencoded").build();

		Response response = client.newCall(request).execute();

		System.out.println(response.body().string());
	}
}