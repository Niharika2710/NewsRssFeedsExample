package com.example.niharika.newsrssfeedsexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

        db.execSQL("CREATE TABLE " + NewsContract.NewsEntry.TABLE_NAME + " ("
                + NewsContract.NewsEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NewsContract.NewsEntry.COLUMN_TITLE + " TEXT,"
                + NewsContract.NewsEntry.COLUMN_PUB_DATE + " TEXT NOT NULL,"
                + NewsContract.NewsEntry.COLUMN_DESC + " TEXT NOT NULL,"
                + NewsContract.NewsEntry.COLUMN_PHOTO_URL + " TEXT NOT NULL"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NewsContract.NewsEntry.TABLE_NAME);
        onCreate(db);
    }


}
