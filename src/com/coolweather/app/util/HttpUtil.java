package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpUtil {
	public static void senHttpRequest(final String address,final HttpCallbackListener listener){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpsURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpsURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(5 * 1000);
					connection.setReadTimeout(5 * 1000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					if(listener != null){
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					if(listener != null)
						connection.disconnect();
				}
			}
		}).start();
	}
}
