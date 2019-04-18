package ch.supsi.dti.isin.meteoapp.network;

import java.util.ArrayList;
import java.util.List;

public class JSONRoot {
    String name;

     String description;
     Main main;

     List<Weather> weather = new ArrayList<>();


    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public JSONRoot(String name, String description) {
        this.name = name;
        this.description = description;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}
