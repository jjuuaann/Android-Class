package com.example.juan.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import static com.example.juan.weatherapp.R.id.btnSubmit;

public class MainActivity extends AppCompatActivity {

    TextView tvEmpty;
    EditText etCity, etState;
    ArrayList<Favorite> favorites;

    ListView listFav;
    RecyclerView rvfave;
    public static final String CITY_KEY = "city";
    public static final String STATE_KEY = "state";
    public static final String FAVORITES_KEY = "FAVORITES";
    FavoriteCityAdapter adapter;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureActivity();

        load();

        if (getIntent().getExtras() != null && !getIntent().getExtras().getString(CityWeatherActivity.EXCEPTION_KEY, "").equals(""))
            Toast.makeText(this, getIntent().getExtras().getString(CityWeatherActivity.EXCEPTION_KEY), Toast.LENGTH_SHORT).show();

    }

    private void configureActivity()
    {
        etCity = (EditText) findViewById(R.id.etCity);
        etState = (EditText) findViewById(R.id.etState);
        tvEmpty = (TextView) findViewById(R.id.tvEmpty);
        //rvfave = (RecyclerView) findViewById(R.id.rvFavorited);
        submit = (Button) findViewById(btnSubmit);
        listFav = (ListView) findViewById(R.id.lvFavorites);

        listFav.setEmptyView(findViewById(R.id.tvEmpty));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityWeatherActivity.class);
                intent.putExtra(CITY_KEY, etCity.getText().toString());
                intent.putExtra(STATE_KEY, etState.getText().toString());
                startActivity(intent);
            }
        });
        listFav.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                favorites.remove(position);
                adapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.FAVORITES_KEY, Context.MODE_PRIVATE);
                Gson gson = new Gson();
                String newJSONFavorite = gson.toJson(favorites);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(MainActivity.FAVORITES_KEY, newJSONFavorite);
                editor.apply();
                Toast.makeText(MainActivity.this, getResources().getString(R.string.favoriteDeleted), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void load()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.FAVORITES_KEY, Context.MODE_PRIVATE);
        String currentJSON = sharedPreferences.getString(MainActivity.FAVORITES_KEY, "");

        if(!currentJSON.equals(""))
        {
            Log.d("thenITS", currentJSON);
            favorites = new ArrayList<Favorite>();
            Gson gson = new Gson();
            favorites = gson.fromJson(currentJSON, new TypeToken<ArrayList<Favorite>>(){}.getType());
            adapter = new FavoriteCityAdapter(this, R.layout.favorite, favorites);
            listFav.setAdapter(adapter);
            adapter.setNotifyOnChange(true);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        load();
    }

}
