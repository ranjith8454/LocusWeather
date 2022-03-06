package com.example.locusweather;

public class WeatherReportObject {
    String main,temp,description,feelslike;

    public WeatherReportObject(String main, String temp, String description, String feelslike) {
        this.main = main;
        this.temp = temp;
        this.description = description;
        this.feelslike = feelslike;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeelslike() {
        return feelslike;
    }

    public void setFeelslike(String feelslike) {
        this.feelslike = feelslike;
    }
}
