package com.example.niharika.newsrssfeedsexample;

import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;

/**
 * Created by Niharika on 5/28/2016.
 */
public class NewsLoader extends CursorLoader {
    public static NewsLoader newAllArticlesInstance(Context context) {
        return new NewsLoader(context, NewsContract.NewsEntry.buildDirUri());
    }

    public static NewsLoader newInstanceForItemId(Context context, long itemId) {
        return new NewsLoader(context, NewsContract.NewsEntry.buildItemUri(itemId));
    }

    private NewsLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, null, null, NewsContract.NewsEntry.DEFAULT_SORT);
    }

    public interface Query {
        String[] PROJECTION = {
                NewsContract.NewsEntry.COLUMN_ID,
                NewsContract.NewsEntry.COLUMN_TITLE,
                NewsContract.NewsEntry.COLUMN_PUB_DATE,
                NewsContract.NewsEntry.COLUMN_DESC,
                NewsContract.NewsEntry.COLUMN_PHOTO_URL,

        };

        int COLUMN_ID = 0;
        int COLUMN_TITLE = 1;
        int COLUMN_PUB_DATE = 2;
        int COLUMN_DESC = 3;
        int COLUMN_PHOTO_URL = 4;
    }
}


