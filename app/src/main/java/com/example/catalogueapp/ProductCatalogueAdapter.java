package com.example.catalogueapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catalogueapp.database.Product;

import java.util.List;


public class ProductCatalogueAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductViewHolder pHolder = (ProductViewHolder)holder;
        if( products != null ){
            Product p = products.get(position);
            pHolder.productName.setText(p.name);
        }else{
            pHolder.productName.setText("Producto no encontrado");
        }
    }

    @Override
    public int getItemCount(){
        if( products != null){
            return products.size();
        }
        return 0;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView productName;

        public ProductViewHolder(View itemView){
            super(itemView);
            // change this!
            productName = itemView.findViewById(R.id.recyclerCompanyName);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.d("CLICK!!" , "CLICKED ON ME: " + productName.getText());
                    callTo.goToNextScreen(getAdapterPosition());
                }
            });
        }
    }

    private final LayoutInflater inflater;
    private List<Product> products;

    MainActivity callTo;
    public ProductCatalogueAdapter(MainActivity context){
        inflater = LayoutInflater.from(context);
        callTo = context;
    }

    public void setProducts(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }
}
