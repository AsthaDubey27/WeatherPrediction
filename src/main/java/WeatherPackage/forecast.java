package WeatherPackage;
public class forecast {
    private String date;
    private int temperature;
    private String weather;

    public forecast(String date, int temperature, String weather) {
        this.date = date;
        this.temperature = temperature;
        this.weather = weather;
    }

    public String getDate() {
        return date;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getWeather() {
        return weather;
    }
}
