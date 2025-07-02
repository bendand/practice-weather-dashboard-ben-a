package com.example.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

public class WeatherApplication {
	private static final Scanner scanner = new Scanner(System.in);
	private static final String APIKey = System.getenv("APIKey");
	private static final RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) throws JsonProcessingException {
		System.out.println("---------------------------------");
		System.out.println(" Welcome to the Weather Dashboard!");
		System.out.println("---------------------------------");
		System.out.println();

		displayWeather("Saint Louis");

		try {
			mainMenu();
		} catch (JsonProcessingException e) {
			System.out.println("Error processing weather data.");
		}
	}

	private static void mainMenu() throws JsonProcessingException {
		while (true) {
			System.out.println("\nEnter a number to view forecast:");
			System.out.println("1. Chicago");
			System.out.println("2. New York");
			System.out.println("3. Los Angeles");
			System.out.println("4. Exit");

			String choice = scanner.nextLine();

			switch (choice) {
				case "1":
					displayWeather("Chicago");
					break;
				case "2":
					displayWeather("New York");
					break;
				case "3":
					displayWeather("Los Angeles");
					break;
				case "4":
					System.out.println("Weather dashboard exited!");
					return;
				default:
					System.out.println("Invalid choice, try again.");
			}
		}
	}

	private static void displayWeather(String city) throws JsonProcessingException {
		System.out.println("\nToday's weather in " + city + ":");
		fetchWeatherData(city);
	}

	private static void fetchWeatherData(String cityName) throws JsonProcessingException {
		String cityFormatted = cityName.replace(' ', '+');
		String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityFormatted + "&appid=" + APIKey + "&units=imperial";

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		ObjectMapper mapper = new ObjectMapper();

		JsonNode root = mapper.readTree(response.getBody());
		JsonNode weatherData = root.path("weather");
		JsonNode mainWeatherObj = root.path("main");

		String humidity = mainWeatherObj.path("humidity").asText();
		String temp = mainWeatherObj.path("temp").asText();
		String description = weatherData.get(0).path("description").asText();

		System.out.printf("Temperature: %s°F\n", temp);
		System.out.printf("Humidity: %s%%\n", humidity);
		System.out.println("Conditions: " + description);
	}
}