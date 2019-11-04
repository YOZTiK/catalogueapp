package com.example.catalogueapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class}, exportSchema = false, version = 2)
public abstract class CatalogueDatabase extends RoomDatabase {

    public abstract ProductDAO productDao();

}
