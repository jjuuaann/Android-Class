package com.example.juan.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Juan on 6/27/2017.
 */

public class FavoriteCityAdapter extends ArrayAdapter<Favorite> {
    ArrayList<Favorite> favorites;
    int resource;


    //List<CityForecast> favoriteCities = Collections.emptyList();
    Context context;
    SharedPreferences preferences;

    public FavoriteCityAdapter(Context context, int resource, ArrayList<Favorite> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.favorites = objects;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }

        Favorite current = favorites.get(position);

        TextView tvPlace = (TextView) convertView.findViewById(R.id.tvFavoritePlace);
        TextView tvTemperature = (TextView) convertView.findViewById(R.id.tvFavoriteTemperature);
        TextView tvUpdate = (TextView) convertView.findViewById(R.id.tvFavoriteUpdated);

        tvPlace.setText(current.city + ", " + current.state);
        tvTemperature.setText(current.temperature);
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String newDate = formatter.format(current.storingDate);
        tvUpdate.setText(context.getResources().getString(R.string.updated) + " " + newDate);

        return convertView;
    }
}
