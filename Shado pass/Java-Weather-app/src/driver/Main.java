package driver;

import windows.*;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Main {

	private static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	public static void main(String[] args) {

		// write your code here

		executor.submit(new Runnable() {
			@Override
			public void run() {
				MainWindow window = new MainWindow();
			}
		});
	}

	public static CompletableFuture<String> getWeatherData(String city) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				String key = "15ef7515ef7a18f9396055cfcf49301c";
				String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key;
				URL urlForGetRequest = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();

				connection.setRequestMethod("GET");
				int responseCode = connection.getResponseCode();

				String line = null;

				if (responseCode == HttpURLConnection.HTTP_OK) {

					BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					StringBuilder builder = new StringBuilder();

					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}

					reader.close();

					return builder.toString();
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}, executor);
	}

	public static CompletableFuture<Double> parserWeatherData(final String data) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				Object obj = new org.json.simple.parser.JSONParser().parse(data);
				var jo = (org.json.simple.JSONObject) obj;
				var inner = (org.json.simple.JSONObject) jo.get("main");

				return (Double) inner.get("temp");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}, executor);
	}
}
