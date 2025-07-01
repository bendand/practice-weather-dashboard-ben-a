package com.example.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

@SpringBootApplication
public class WeatherApplication {
	private static final Scanner scanner = new Scanner(System.in);
	private static final String APIKey = System.getenv("APIKey");
	public static RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(WeatherApplication.class, args);

		System.out.println("---------------------------------");
		System.out.println(" Welcome to the Weather Dashboard!");
		System.out.println("---------------------------------");

		String url = "https://api.openweathermap.org/data/2.5/weather?q=Saint+Louis&appid=" + APIKey + "&units=imperial";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getBody());
		JsonNode weatherData = root.path("weather");
		JsonNode weatherObj = weatherData.get(0);

		System.out.println(weatherObj.get("description"));

//		for (JsonNode node : weatherData) {
//			System.out.println(node);
//		}



		System.out.println("Today's weather in Saint Louis: ");
//		System.out.println(root);
//		System.out.println(weatherData);

//		System.out.println(name);



//		mainMenu();
	}

	public static void mainMenu() {
		while (true) {
			System.out.println("Enter a number to view forecast of the corresponding city, or to exit");
			System.out.println("1. Chicago");
			System.out.println("2. New York");
			System.out.println("3. Los Angeles");
			System.out.println("4. Exit");

			String choice = scanner.nextLine();

			switch (choice) {
				case ("1"):
					System.out.println("Chicago weather data here");
					break;
				case ("2"):
					System.out.println("New York weather data here");
					break;
				case ("3"):
					System.out.println("Los Angeles weather data here");
					break;
				case ("4"):
					System.out.println("Weather dashboard exited!");
					break;
				default:
					System.out.println("Invalid choice, try again");
			}
		}

	}

}
