package com.example.juan.weatherapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;

public class CityWeatherActivity extends AppCompatActivity implements GetWeather.IData{

    ProgressDialog pdLoading;
    //List<CityForecast.DayForecast> forecasts;
    String city, state;

    TextView tvPlace;
    ListView forcastList;
    ArrayList<HourlyForecast> forecasts;
    //String city, state;

    public static final String FORECAST_KEY = "SELETED_FORECAST";
    public static final String CITY_KEY = "SELETED_CITY";
    public static final String STATE_KEY = "SELETED_STATE";
    public static final String EXCEPTION_KEY = "NOTHING_RECEIVED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        if (getIntent().getExtras().getString(MainActivity.CITY_KEY) != null && getIntent().getExtras().getString(MainActivity.STATE_KEY) != null)
        {
            city = getIntent().getExtras().getString(MainActivity.CITY_KEY);
            state = getIntent().getExtras().getString(MainActivity.STATE_KEY);
        }

        configureActivity();

        pdLoading.show();
        new GetWeather(this, city, state).execute();
    }

    private void configureActivity()
    {

        tvPlace = (TextView) findViewById(R.id.cw_tvPlace);
        tvPlace.setText(city + ", " + state);
        forcastList = (ListView) findViewById(R.id.cw_lvForecasts);
        pdLoading = new ProgressDialog(this);
        pdLoading.setMessage(getResources().getString(R.string.loading));
        pdLoading.setCancelable(false);
       // pdLoading.show();

        forcastList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HourlyForecast selectedForecast = forecasts.get(position);
                Intent intent = new Intent(CityWeatherActivity.this, DetailActivity.class);
                intent.putExtra(FORECAST_KEY, selectedForecast);
                intent.putExtra(CITY_KEY, city);
                intent.putExtra(STATE_KEY, state);
                startActivity(intent);
            }
        });

    }

    private void addFavorite() {
        ArrayList<Favorite> currentFavorites = new ArrayList<Favorite>();
        int actionType = 1;
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.FAVORITES_KEY, Context.MODE_PRIVATE);
        String currentJSON = sharedPreferences.getString(MainActivity.FAVORITES_KEY, "");

        if (!currentJSON.equals(""))
            currentFavorites = new Gson().fromJson(currentJSON, new TypeToken<ArrayList<Favorite>>(){}.getType());

        Favorite newFavorite = new Favorite(city, state, forecasts.get(0).getTemperature(), Calendar.getInstance().getTime());

        boolean exists = false;
        for (int i=0; i<currentFavorites.size(); i++)
        {
            Favorite f = currentFavorites.get(i);
            if (f.city.equals(city) && f.state.equals(state))
            {
                currentFavorites.set(i, newFavorite);
                exists = true;
                actionType = 2; //updating record
                break;
            }
        }

        if (!exists)
            currentFavorites.add(newFavorite);

        Gson gson = new Gson();
        String newJSONFavorite = gson.toJson(currentFavorites);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.FAVORITES_KEY, newJSONFavorite);
        editor.apply();

        if (actionType == 1)
            Toast.makeText(this, getResources().getString(R.string.favAdded), Toast.LENGTH_SHORT).show();
        else if (actionType == 2)
            Toast.makeText(this, getResources().getString(R.string.favUpdated), Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreate(savedInstanceState);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.fav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuAddFavorite) {
            addFavorite();
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    public void displayForecast(ArrayList<HourlyForecast> hourlyForecasts, String error) {
        forecasts = hourlyForecasts;

        pdLoading.dismiss();

        if (forecasts != null) {
            HourlyAdapter hourlyAdapter = new HourlyAdapter(this, R.layout.hourly, forecasts);
            forcastList.setAdapter(hourlyAdapter);
            hourlyAdapter.setNotifyOnChange(true);
        } else {
            final Intent intent = new Intent(this, MainActivity.class);
            if (error == null)
                error = getResources().getString(R.string.noData);

            if (error.equals("No cities match your search query"))
            {
                Toast.makeText(this, error + ". " + getResources().getString(R.string.redirecting), Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                }, 5000);
            }
            else
            {
                intent.putExtra(EXCEPTION_KEY, error);
                startActivity(intent);
            }
        }

    }

}
