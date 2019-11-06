package com.example.catalogueapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.catalogueapp.database.DatabaseTask;
import com.example.catalogueapp.database.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VolleyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("onCreate Method", "From MainActivity ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        getJsonRequest();
    }

    public void getJsonRequest() {
        Log.d("getJsonRequest", "Started");

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://10.0.2.2:8000";
//        String url = "http://192.168.1.72:8000";

        Log.d("URL to connect with node", "String: "+url);
        final DatabaseTask databaseTask = new DatabaseTask(getApplicationContext(), null);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("companies");
                            Log.d("Response", "onResponse: "+response);

                            Product[] products = new Product[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject empresa = jsonArray.getJSONObject(i);

                                String id = empresa.getString("id");
                                String name = empresa.getString("name");
                                String desc = empresa.getString("desc");
                                String img = empresa.getString("image");
                                int ranking = empresa.getInt("ranking");

                                Product e = new Product();
                                e.emp_id = id;
                                e.name = name;
                                e.description = desc;
                                e.image = img;
                                e.ranking = ranking;

                                products[i] = e;
                            }

                            databaseTask.execute(products);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }
}