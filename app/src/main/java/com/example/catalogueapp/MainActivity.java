package com.example.catalogueapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.catalogueapp.database.CatalogueDatabase;
import com.example.catalogueapp.database.DatabaseReceiver;
import com.example.catalogueapp.database.DatabaseTask;
import com.example.catalogueapp.database.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatabaseReceiver {

    public static String MESSAGE = "com.example.catalogueApp.MainActivity";
    ProductViewModel products;
    public List<Product> resultProducts;
    ProductCatalogueAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        products = ViewModelProviders.of(this).get(ProductViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new ProductCatalogueAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
        products.getProducts(getApplicationContext()).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                Log.d("CHANGED HERE!","ENTERED " + products);
                getAll(products);
            }
        });*/
    }

    public void getAll(List<Product> products) {
/*
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup table = findViewById(R.id.catalogueList);
        table.removeAllViews();
        for (Product actual:products) {z<
            Log.d("Hello","PRODUCTS NAME HERE! " + actual.name);
            View row = inflater.inflate(R.layout.row_layout,table, false );
            TextView vw = row.findViewById(R.id.productName);
            vw.setText(actual.name);
            table.addView(row);
        }
 */
    }

    public void doAction(View view) {
        String src = "%" + ((EditText) findViewById(R.id.searchText)).getText() + "%";
        Log.d("SEARCH STRING!!!", "ENTERED: " + src);
        products.searchProducts(getApplicationContext(), src).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                //getAll(products);
                Log.d("SET resultProducts", "FOUND: " + products);
                resultProducts = products;

                adapter.setProducts(products);
//                Log.d("Changed HERE!!!", "ENTERED: " + products);
            }
        });
        /*
        // version 2 to obtain products from database!
        DatabaseTask task = new DatabaseTask(getApplicationContext(), this);
        Product p = new Product();
        p.name = "%"+((EditText)findViewById(R.id.searchText)).getText()+"%";
        task.execute(p);
        /*
        Log.d("CUSTOM","CLICK ON  ME!");
        TextView v = findViewById(R.id.editText);
        TextView vt = findViewById(R.id.viewTitleText);
        vt.setText(v.getText());
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(MESSAGE,""+v.getText());
        Log.d("TEXT " ,"" + v.getText());
        startActivity(intent);*/
    }

    public void goToWeb(View view) {
        Log.d("goToWeb Method", "From: " + view);
        Intent goToNextActivity = new Intent(getApplicationContext(), VolleyActivity.class);
        startActivity(goToNextActivity);
    }

    public void goToNextScreen(final int adapterPosition) {

        Intent i = new Intent(this, DetailActivity.class);

        //Log.d("RESULT PRODUCTS!!!", "RESULT OF SEARCH: " + resultProducts);
        //Log.d("NAME PRODUCT", "RESULT OF SELECT: " + resultProducts.get(adapterPosition).name);
        i.putExtra("name", resultProducts.get(adapterPosition).name);
        //Log.d("DESCRIPTION PRODUCT", "RESULT OF SELECT: " + resultProducts.get(adapterPosition).description);
        i.putExtra("desc", resultProducts.get(adapterPosition).description);
        i.putExtra("image", resultProducts.get(adapterPosition).image);
        i.putExtra("index", resultProducts.get(adapterPosition).emp_id);
        i.putExtra("rating", ""+resultProducts.get(adapterPosition).ranking);
        Log.d("RATING ON DB", "STARTS: " + resultProducts.get(adapterPosition).ranking);
        startActivity(i);
    }

    public void showMeDBInfo(View view) {
        products.searchProducts(getApplicationContext(), "%%").observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                //getAll(products);
                Log.d("DB INFO FOUND", "PRODUCTS: " + products);
            }
        });
    }
}
