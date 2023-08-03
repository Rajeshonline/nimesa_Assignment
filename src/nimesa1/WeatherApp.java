package nimesa1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherApp {
    private static final String API_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";

    public static void main(String[] args) {
        WeatherApp app = new WeatherApp();
        app.run();
    }

    private void run() {
        int choice;
        do {
            displayMenu();
            choice = getUserChoice();
            switch (choice) {
                case 1:
                    getWeather();
                    break;
                case 2:
                    getWindSpeed();
                    break;
                case 3:
                    getPressure();
                    break;
                case 0:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private void displayMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Get weather");
        System.out.println("2. Get wind speed");
        System.out.println("3. Get pressure");
        System.out.println("0. Exit");
    }

    private int getUserChoice() {
        System.out.print("Enter your choice: ");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading input. Please try again.");
            return -1;
        }
    }

    private JSONObject getWeatherData() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                return new JSONObject(response.toString());
            } else {
                System.out.println("Error: Unable to retrieve weather data. Please try again later.");
                return null;
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to retrieve weather data. Please check your internet connection.");
            return null;
        }
    }

    private void getWeather() {
        JSONObject weatherData = getWeatherData();
        if (weatherData != null) {
            System.out.print("Enter the date (format: yyyy-MM-dd HH:mm:ss): ");
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String targetDate = reader.readLine();

                JSONArray list = weatherData.getJSONArray("list");
                for (int i = 0; i < list.length(); i++) {
                    JSONObject data = list.getJSONObject(i);
                    String dt_txt = data.getString("dt_txt");

                    if (dt_txt.contains(targetDate)) {
                        JSONObject main = data.getJSONObject("main");
                        double temp = main.getDouble("temp");
                        System.out.println("Temperature on " + dt_txt + " : " + temp + "°C");
                        return;
                    }
                }
                System.out.println("No data found for the specified date.");
            } catch (IOException e) {
                System.out.println("Error reading input. Please try again.");
            }
        }
    }

    private void getWindSpeed() {
        JSONObject weatherData = getWeatherData();
        if (weatherData != null) {
            System.out.print("Enter the date (format: yyyy-MM-dd HH:mm:ss): ");
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String targetDate = reader.readLine();

                JSONArray list = weatherData.getJSONArray("list");
                for (int i = 0; i < list.length(); i++) {
                    JSONObject data = list.getJSONObject(i);
                    String dt_txt = data.getString("dt_txt");

                    if (dt_txt.contains(targetDate)) {
                        JSONObject wind = data.getJSONObject("wind");
                        double windSpeed = wind.getDouble("speed");
                        System.out.println("Wind Speed on " + dt_txt + " : " + windSpeed + " m/s");
                        return;
                    }
                }
                System.out.println("No data found for the specified date.");
            } catch (IOException e) {
                System.out.println("Error reading input. Please try again.");
            }
        }
    }

    private void getPressure() {
        JSONObject weatherData = getWeatherData();
        if (weatherData != null) {
            System.out.print("Enter the date (format: yyyy-MM-dd HH:mm:ss): ");
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String targetDate = reader.readLine();

                JSONArray list = weatherData.getJSONArray("list");
                for (int i = 0; i < list.length(); i++) {
                    JSONObject data = list.getJSONObject(i);
                    String dt_txt = data.getString("dt_txt");

                    if (dt_txt.contains(targetDate)) {
                        JSONObject main = data.getJSONObject("main");
                        double pressure = main.getDouble("pressure");
                        System.out.println("Pressure on " + dt_txt + " : " + pressure + " hPa");
                        return;
                    }
                }
                System.out.println("No data found for the specified date.");
            } catch (IOException e) {
                System.out.println("Error reading input. Please try again.");
            }
        }
    }
}
