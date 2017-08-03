package com.example.juan.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class DetailActivity extends AppCompatActivity {

    TextView place, temperature, climate, maxTemp, minTemp, feelsLike, humidity, dewpoint, pressure, clouds, winds;
    ImageView ivIcon;
    HourlyForecast selected;
    String city, state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        place = (TextView) findViewById(R.id.tvDetailPlace);
        ivIcon = (ImageView) findViewById(R.id.ivDetailIcon);
        temperature = (TextView) findViewById(R.id.tvDetailTemperature);
        climate = (TextView) findViewById(R.id.tvDetailClimateType);
        maxTemp = (TextView) findViewById(R.id.tvDetailMaxTemp);
        minTemp = (TextView) findViewById(R.id.tvDetailMinTemp);
        feelsLike = (TextView) findViewById(R.id.tvDetailFeelsLike);
        humidity = (TextView) findViewById(R.id.tvDetailHumidity);
        dewpoint = (TextView) findViewById(R.id.tvDetailDewpoint);
        pressure = (TextView) findViewById(R.id.tvDetailPressure);
        clouds = (TextView) findViewById(R.id.tvDetailClouds);
        winds = (TextView) findViewById(R.id.tvDetailWinds);

        if (getIntent() != null) {
            city = getIntent().getExtras().getString(CityWeatherActivity.CITY_KEY);
            state = getIntent().getExtras().getString(CityWeatherActivity.STATE_KEY);
            selected = (HourlyForecast) getIntent().getExtras().getSerializable(CityWeatherActivity.FORECAST_KEY);
            showForecast();
        }
    }

    private void showForecast()
    {
        place.setText(" " + city + ", " + state + " (" + selected.getTime() + ")");
        //place.setText(" " + city + ", " + state + " (" +  ")");
        Picasso.with(this).load(selected.getIconUrl()).resize(200,200).into(ivIcon);
        temperature.setText(selected.getTemperature());
        climate.setText(selected.getClimateType());
        maxTemp.setText(selected.getMaximumTemp());
        minTemp.setText(selected.getMinimumTemp());
        feelsLike.setText(selected.getFeelsLike());
        humidity.setText(selected.getHumidity());
        dewpoint.setText(selected.getDewpoint());
        pressure.setText(selected.getPressure());
        clouds.setText(selected.getClouds());
        winds.setText(selected.getWindSpeed() + ", " + selected.getWindDirection());
    }

    private void addFavorite()
    {
        ArrayList<Favorite> currentFavorites = new ArrayList<Favorite>();
        int actionType = 1;
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.FAVORITES_KEY, Context.MODE_PRIVATE);
        String currentJSON = sharedPreferences.getString(MainActivity.FAVORITES_KEY, "");

        if (!currentJSON.equals(""))
            currentFavorites = new Gson().fromJson(currentJSON, new TypeToken<ArrayList<Favorite>>(){}.getType());

        Favorite newFavorite = new Favorite(city, state, selected.getTemperature(), Calendar.getInstance().getTime());

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
            Toast.makeText(this, "Fave added", Toast.LENGTH_SHORT).show();
        else if (actionType == 2)
            Toast.makeText(this, "Fave updated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.fav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuAddFavorite)
        {
            addFavorite();
        }
        return super.onOptionsItemSelected(item);
    }


}
