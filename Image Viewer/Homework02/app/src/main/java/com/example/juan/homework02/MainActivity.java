package com.example.juan.homework02;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ImageData.IData {
    TextView tvKeyword;
    Button btnGo;
    ImageView image;
    ImageView igLeft;
    ImageView igRight;
    AlertDialog aDKeyword;
    ArrayList<String> imageURLs;
    ProgressDialog loading, adLoadingPictures;
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findView
        tvKeyword = (TextView)findViewById(R.id.tvKeyword);
        btnGo = (Button) findViewById(R.id.btnGo);
        image = (ImageView)findViewById(R.id.imageView);
        igLeft = (ImageView)findViewById(R.id.igPrev);
        igRight = (ImageView)findViewById(R.id.igNext);
        igLeft.setEnabled(false);
        igRight.setEnabled(false);


        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configureAlertDialog();
                aDKeyword.show();
            }
        });

        igRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition<(imageURLs.size()-1))
                {
                    currentPosition = currentPosition + 1;
                }
                else if (currentPosition == (imageURLs.size()-1))
                {
                    currentPosition = 0;
                }
                new GetImage(MainActivity.this).execute(imageURLs.get(currentPosition));
            }
        });

        igLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition == 0)
                {
                    currentPosition = (imageURLs.size()-1);
                }
                else
                {
                    currentPosition = currentPosition - 1;
                }

                new GetImage(MainActivity.this).execute(imageURLs.get(currentPosition));
            }
        });

        configureProgressDiaglog();
        configurePictureProgressDiaglog();

    }



    private void configureAlertDialog()
    {
        AlertDialog.Builder builder;
        ArrayList<String> keywords = new ArrayList<String>();
        keywords.add("UNCC");
        keywords.add("Android");
        keywords.add("Winter");
        keywords.add("Aurora");
        keywords.add("Wonders");
        final CharSequence[] charKeywords = keywords.toArray(new CharSequence[keywords.size()]);
        builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.prompt)).
                setItems(charKeywords, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isConnectedOnline())
                        {
                            tvKeyword.setText(charKeywords[which].toString());
                            new ImageData(MainActivity.this).execute(charKeywords[which].toString());
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        aDKeyword = builder.create();
    }



    @Override
    public void getImagesFromDictionary(String result) {
        imageURLs = new ArrayList<String>();
        imageURLs.addAll(Arrays.asList(result.split(";")));
        imageURLs.remove(0);
        currentPosition = 0;

        if (!isConnectedOnline()){
            Toast.makeText(MainActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
            return;
        }


        if (imageURLs.size() == 0){
            igLeft.setEnabled(false);
            igRight.setEnabled(false);
            image.setImageBitmap(null);
            Toast.makeText(MainActivity.this, "No images found.", Toast.LENGTH_SHORT).show();
        }
        else if (imageURLs.size() == 1){
            igLeft.setEnabled(false);
            igRight.setEnabled(false);
            new GetImage(MainActivity.this).execute(imageURLs.get(currentPosition));
        }
        else
        {
            igLeft.setEnabled(true);
            igRight.setEnabled(true);
            new GetImage(MainActivity.this).execute(imageURLs.get(currentPosition));
        }

    }


    private boolean isConnectedOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void configureProgressDiaglog()
    {
        loading = new ProgressDialog(this);
        loading.setMessage(getResources().getString(R.string.loading_message));
    }
    private void configurePictureProgressDiaglog()
    {
        adLoadingPictures = new ProgressDialog(this);
        adLoadingPictures.setMessage(getResources().getString(R.string.photo_loading_message));
    }


}
