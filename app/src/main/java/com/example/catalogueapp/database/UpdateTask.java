package com.example.catalogueapp.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Room;

import java.util.List;

public class UpdateTask extends AsyncTask<Product,Void, List<Product>> {
    CatalogueDatabase db;
    DatabaseReceiver receiver;

    String id;
    int ranking;

    public UpdateTask(Context ctx , DatabaseReceiver receiver, String comId, int comRanking){
        this.receiver = receiver;
        db = Room.databaseBuilder(ctx,
                CatalogueDatabase.class ,
                "catalogue-database").build();

        id = comId;
        ranking = comRanking;
    }

    @Override
    public void onPreExecute(){
    }
    @Override
    protected List<Product> doInBackground(Product... params) {
        Log.d("UPDATING RATING", "id: "+id+" ranking: "+ranking);
        db.productDao().updateRanking(id, ranking);

        return null;
        /*if( params[0].name.equals(""))
            return db.productDao().getAll();
        return db.productDao().search(params[0].name);
         */
    }
    @Override
    public void onPostExecute(List<Product> result){
        // do something on ui!
        // receiver.getAll(result);
    }
}
