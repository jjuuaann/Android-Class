package com.example.juan.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Juan on 6/27/2017.
 */

public class GetWeather extends AsyncTask<String, Void, ArrayList<HourlyForecast>> {
    IData activity;
    String api, error;

    public GetWeather(IData activity, String city, String state) {
        this.activity = activity;
        String key = ((Context)activity).getResources().getString(R.string.api_key);
        city = city.replaceAll(" ", "_");
        api = "http://api.wunderground.com/api/"+ key + "/hourly/q/" + state + "/"+city+ ".json";
    }


    @Override
    protected ArrayList<HourlyForecast> doInBackground(String... params) {
        BufferedReader reader = null;
        try
        {
            URL url = new URL(api);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK)
            {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return Util.HourlyParser.parseForecasts(sb.toString(), (Context)activity);
            }
        }
        catch (Exception ex)
        {
            Log.d("error_caughtE", ex.getMessage().toString());
            error = ex.getMessage().toString();
        }
        catch (Throwable ex)
        {
            Log.d("error_caughtT", ex.getMessage().toString());
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                Log.d("error_caught", e.getMessage().toString());
            }
            catch (Exception ex)
            {
                Log.d("error_caught", ex.getMessage().toString());
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<HourlyForecast> hourlyForecasts) {
        super.onPostExecute(hourlyForecasts);
        activity.displayForecast(hourlyForecasts, error);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    static public interface IData{
        void displayForecast(ArrayList<HourlyForecast> hourlyForecasts, String error);
    }
}
