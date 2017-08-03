package com.example.juan.homework02;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Juan on 6/6/2017.
 */

public class GetImage extends AsyncTask<String, Void, Bitmap> {
    MainActivity currentActivity;

    public GetImage(MainActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Bitmap image = BitmapFactory.decodeStream(con.getInputStream());
            return image;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        currentActivity.adLoadingPictures.show();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        currentActivity.adLoadingPictures.dismiss();
        currentActivity.image.setImageBitmap(bitmap);
    }



}
