package com.example.vlad.cookhelper.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Manages a local database for movie data.
 */
public class ProductDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "cookhelper.db";

    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create a table to hold movies
//        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + ProductEntry.TABLE_NAME + " (" +
//                ProductEntry._ID + " INTEGER PRIMARY KEY," +
//                ProductEntry.COLUMN_NAME + " TEXT NOT NULL," +
//                ProductEntry.COLUMN_GLASS + " INTEGER," +
//                ProductEntry.COLUMN_FACETED_GLASS + " INTEGER," +
//                ProductEntry.COLUMN_TABLESPOON + " INTEGER," +
//                ProductEntry.COLUMN_TEASPOON + " INTEGER" +
//                " );";
//
//        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        db.execSQL("DROP TABLE IF EXISTS " + ProductContract.ProductEntry.TABLE_NAME);
        onCreate(db);
    }
}
