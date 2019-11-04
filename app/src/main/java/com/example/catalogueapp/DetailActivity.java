package com.example.catalogueapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.catalogueapp.database.CatalogueDatabase;
import com.example.catalogueapp.database.Product;
import com.example.catalogueapp.database.UpdateTask;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    CatalogueDatabase db;
    ProductViewModel products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView coName = findViewById(R.id.companyName);
        TextView coDescription = findViewById(R.id.companyDescription);
        ImageView iv = (ImageView) findViewById(R.id.iv);

        products = ViewModelProviders.of(this).get(ProductViewModel.class);

        final String id = ""+getIntent().getExtras().getString("index");
        String coname = getIntent().getExtras().getString("name");
        String codesc = getIntent().getExtras().getString("desc");
        String imageUrl = getIntent().getExtras().getString("image");
        final int ranking = Integer.parseInt(getIntent().getExtras().getString("rating"));

        Log.d("RATING", ranking+"");

        coName.setText(coname+"");
        coDescription.setText(codesc+"");

        new DownLoadImageTask(iv).execute(imageUrl);

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating(ranking);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Log.d("onRatingChanged", "");
                UpdateTask ut = new UpdateTask(getApplicationContext(), null, id, (int) ratingBar.getRating());
                ut.execute();
            }
        });

    }

    private class DownLoadImageTask extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}