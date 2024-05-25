package WeatherPackage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MyServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("index.html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String apiKey = "86c6d71459b39f8cbab89cb1043337c0";
        String city = request.getParameter("city");
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            Scanner scanner = new Scanner(reader);
            StringBuilder responseContent = new StringBuilder();

            while (scanner.hasNext()) {
                responseContent.append(scanner.nextLine());
            }

            scanner.close();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseContent.toString(), JsonObject.class);

            long dateTimestamp = jsonObject.get("dt").getAsLong() * 1000;
            String date = new Date(dateTimestamp).toString();

            double temperatureKelvin = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
            int temperatureCelsius = (int) (temperatureKelvin - 273.15);

            int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();

            double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();

            String weatherCondition = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();

            String recommendation = generateRecommendation(weatherCondition, temperatureCelsius);

            request.setAttribute("date", date);
            request.setAttribute("city", city);
            request.setAttribute("temperature", temperatureCelsius);
            request.setAttribute("weatherCondition", weatherCondition);
            request.setAttribute("humidity", humidity);
            request.setAttribute("windSpeed", windSpeed);
            request.setAttribute("weatherData", responseContent.toString());
            request.setAttribute("recommendation", recommendation);
            
         // Fetch 5-day forecast
            String forecastUrl = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + apiKey;
            url = new URL(forecastUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            inputStream = connection.getInputStream();
            reader = new InputStreamReader(inputStream);
            scanner = new Scanner(reader);
            StringBuilder forecastResponseContent = new StringBuilder();

            while (scanner.hasNext()) {
                forecastResponseContent.append(scanner.nextLine());
            }
            scanner.close();

            JsonObject forecastJson = gson.fromJson(forecastResponseContent.toString(), JsonObject.class);
            JsonArray forecastList = forecastJson.getAsJsonArray("list");

            List<forecast> forecastDataList = new ArrayList<>();
            for (int i = 0; i < forecastList.size(); i += 8) { // This loop is where you process forecast data
                JsonObject forecastObject = forecastList.get(i).getAsJsonObject();
                long forecastTimestamp = forecastObject.get("dt").getAsLong() * 1000;
                String forecastDate = new Date(forecastTimestamp).toString();
                double forecastTempKelvin = forecastObject.getAsJsonObject("main").get("temp").getAsDouble();
                int forecastTempCelsius = (int) (forecastTempKelvin - 273.15);
                String forecastWeather = forecastObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();

                forecastDataList.add(new forecast(forecastDate, forecastTempCelsius, forecastWeather));
            }

            request.setAttribute("forecastDataList", forecastDataList);
            System.out.println("Forecast Response: " + forecastResponseContent.toString());



            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
    
    

    private String generateRecommendation(String weatherCondition, int temperatureCelsius) {
        String recommendation = "";

        switch (weatherCondition.toLowerCase()) {
            case "rain":
                recommendation = "It's raining. Don't forget to take an umbrella!";
                break;
            case "clear":
                recommendation = "It's clear and sunny. A great day to wear sunglasses.";
                break;
            case "snow":
                recommendation = "It's snowing. Stay warm and drive safely.";
                break;
            case "clouds":
                recommendation = "It's cloudy. You might need a light jacket.";
                break;
            default:
                recommendation = "Weather looks fine. Have a great day!";
                break;
        }

        if (temperatureCelsius < 0) {
            recommendation += " It's freezing outside, dress warmly.";
        } else if (temperatureCelsius > 30) {
            recommendation += " It's hot outside, stay hydrated.";
        }

        return recommendation;
    }
}
    
    
    
 