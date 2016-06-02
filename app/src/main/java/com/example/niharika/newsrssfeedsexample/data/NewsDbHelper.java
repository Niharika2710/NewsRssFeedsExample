package com.example.niharika.newsrssfeedsexample.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.niharika.newsrssfeedsexample.data.NewsProvider.Tables;

/**
 * Created by Niharika on 5/28/2016.
 */

public class NewsDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "news.db";
    private static final int DATABASE_VERSION = 1;

    public NewsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.ITEMS + " ("
                + NewsContract.ItemsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NewsContract.ItemsColumns.TITLE + " TEXT NOT NULL,"
                + NewsContract.ItemsColumns.BODY + " TEXT NOT NULL,"
                + NewsContract.ItemsColumns.PHOTO_URL + " TEXT NOT NULL,"
                + NewsContract.ItemsColumns.PUBLISHED_DATE + " INTEGER NOT NULL DEFAULT 0"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tables.ITEMS);
        onCreate(db);
    }


}
