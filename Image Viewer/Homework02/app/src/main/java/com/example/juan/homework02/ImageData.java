package com.example.juan.homework02;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Juan on 6/7/2017.
 */

public class ImageData extends AsyncTask<String, Void, String> {

    final String api = "http://dev.theappsdr.com/apis/photos/index.php?keyword=";
    MainActivity currentActivity;

    public ImageData(MainActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        BufferedReader reader = null;


        try {
            URL url = new URL(api + params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        currentActivity.loading.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        currentActivity.loading.dismiss();
        currentActivity.getImagesFromDictionary(s);
    }
    static public interface IData
    {
        public void getImagesFromDictionary(String result);
    }
}
