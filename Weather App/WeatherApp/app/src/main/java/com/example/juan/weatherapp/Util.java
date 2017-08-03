package com.example.juan.weatherapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Juan on 6/27/2017.
 */

public class Util {


    static public class HourlyParser {

        static ArrayList<HourlyForecast> parseForecasts(String json, Context ctx) throws Exception {
            ArrayList<HourlyForecast> forecasts = new ArrayList<HourlyForecast>();
            String eng_temp_abbr = ctx.getResources().getString(R.string.english_temp_abreviation);
            String eng_temp = ctx.getResources().getString(R.string.english_temp);
            String pressure_unit = ctx.getResources().getString(R.string.pressure);
            String speed_unit = ctx.getResources().getString(R.string.speed);

            JSONObject root = new JSONObject(json);
            JSONObject response = root.getJSONObject("response");

            if (response.has("error"))
                throw new Exception(response.getJSONObject("error").getString("description"));

            JSONArray hourly_forecasts = root.getJSONArray("hourly_forecast");

            for (int i=0; i<hourly_forecasts.length(); i++)
            {
                HourlyForecast hourlyForecast = new HourlyForecast();

                JSONObject forecast = hourly_forecasts.getJSONObject(i);
                JSONObject FCTTIME = forecast.getJSONObject("FCTTIME");
                hourlyForecast.setTime(FCTTIME.getString("civil"));

                hourlyForecast.setIconUrl(forecast.getString("icon_url"));

                JSONObject temp = forecast.getJSONObject("temp");
                hourlyForecast.setTemperature(temp.getString("english") + eng_temp_abbr);
                //only for now:
                //hourlyForecast.setMaximumTemp(temp.getString("english") + " " + eng_temp);
                //hourlyForecast.setMinimumTemp(temp.getString("english") + " " + eng_temp);

                hourlyForecast.setClimateType(forecast.getString("wx"));

                JSONObject feelsLike = forecast.getJSONObject("feelslike");
                hourlyForecast.setFeelsLike(feelsLike.getString("english"));

                hourlyForecast.setHumidity(forecast.getString("humidity") + "%");

                JSONObject dewpoint = forecast.getJSONObject("dewpoint");
                hourlyForecast.setDewpoint(dewpoint.getString("english") + " " + eng_temp);

                JSONObject pressure = forecast.getJSONObject("mslp");
                hourlyForecast.setPressure(pressure.getString("metric") + " " + pressure_unit);

                hourlyForecast.setClouds(forecast.getString("condition"));

                JSONObject windSpeed = forecast.getJSONObject("wspd");
                hourlyForecast.setWindSpeed(windSpeed.getString("english") + speed_unit);

                JSONObject windDirection = forecast.getJSONObject("wdir");
                hourlyForecast.setWindDirection(windDirection.getString("degrees") + "° " + windDirection.getString("dir"));

                forecasts.add(hourlyForecast);
            }

            int smallest = Integer.parseInt(forecasts.get(0).getTemperature().substring(0,2));
            int largest = smallest;
            for (int i=1; i<forecasts.size(); i++)
            {
                if (Integer.parseInt(forecasts.get(i).getTemperature().substring(0,2)) < smallest)
                    smallest = Integer.parseInt(forecasts.get(i).getTemperature().substring(0,2));

                if (Integer.parseInt(forecasts.get(i).getTemperature().substring(0,2)) > largest)
                    largest = Integer.parseInt(forecasts.get(i).getTemperature().substring(0,2));
            }

            for (int i=0; i<forecasts.size(); i++)
            {
                HourlyForecast temp = forecasts.get(i);
                temp.setMaximumTemp(String.valueOf(largest) + " " + eng_temp);
                temp.setMinimumTemp(String.valueOf(smallest) + " " + eng_temp);
                forecasts.set(i, temp);
            }

            return forecasts;
        }

    }

}


