package com.example.juan.weatherapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Juan on 6/27/2017.
 */

public class HourlyAdapter extends ArrayAdapter<HourlyForecast> {
    Context context;
    ArrayList<HourlyForecast> forecasts;
    int resource;

    public HourlyAdapter(Context context, int resource, ArrayList<HourlyForecast> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.forecasts = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }

        HourlyForecast current = forecasts.get(position);

        ImageView ivIcon = (ImageView) convertView.findViewById(R.id.ivHourlyIcon);
        TextView tvHour = (TextView) convertView.findViewById(R.id.tvHourlyHour);
        TextView tvClimate = (TextView) convertView.findViewById(R.id.tvHourlyClimateType);
        TextView tvTemperature = (TextView) convertView.findViewById(R.id.tvHourlyTemperature);

        Picasso.with(context).load(current.getIconUrl()).into(ivIcon);
        tvHour.setText(current.getTime());
        tvClimate.setText(current.getClimateType());
        tvTemperature.setText(current.getTemperature());

        return convertView;
    }
}
