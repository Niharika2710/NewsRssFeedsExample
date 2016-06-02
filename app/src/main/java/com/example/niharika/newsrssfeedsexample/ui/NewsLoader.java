package com.example.niharika.newsrssfeedsexample.ui;

import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;

import com.example.niharika.newsrssfeedsexample.data.NewsContract;

/**
 * Created by Niharika on 5/28/2016.
 */
public class NewsLoader extends CursorLoader {
    public static NewsLoader newAllArticlesInstance(Context context) {
        return new NewsLoader(context, NewsContract.Items.buildDirUri());
    }

    public static NewsLoader newInstanceForItemId(Context context, long itemId) {
        return new NewsLoader(context, NewsContract.Items.buildItemUri(itemId));
    }

    private NewsLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, null, null, NewsContract.Items.DEFAULT_SORT);
    }

    public interface Query {
        String[] PROJECTION = {
                NewsContract.Items._ID,
                NewsContract.Items.TITLE,
                NewsContract.Items.PUBLISHED_DATE,
                NewsContract.Items.BODY,
                NewsContract.Items.PHOTO_URL,

        };

        int COLUMN_ID = 0;
        int COLUMN_TITLE = 1;
        int COLUMN_PUB_DATE = 2;
        int COLUMN_DESC = 3;
        int COLUMN_PHOTO_URL = 4;
    }
}


