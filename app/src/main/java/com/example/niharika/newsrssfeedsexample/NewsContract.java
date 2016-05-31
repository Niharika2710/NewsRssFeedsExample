package com.example.niharika.newsrssfeedsexample;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Niharika on 5/28/2016.
 */
public class NewsContract {

    public static final String CONTENT_AUTHORITY = "com.example.niharika.newsrssfeedsexample";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_NEWS = "news";

    public static final class NewsEntry implements BaseColumns {

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NEWS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NEWS;

        /**
         * Matches: /items/
         */
        public static Uri buildDirUri() {
            return BASE_CONTENT_URI.buildUpon().appendPath(PATH_NEWS).build();
        }

        /**
         * Matches: /items/[_id]/
         */
        public static Uri buildItemUri(long _id) {
            return BASE_CONTENT_URI.buildUpon().appendPath(PATH_NEWS).appendPath(Long.toString(_id)).build();
        }

        /**
         * Read item ID item detail URI.
         */
        public static long getItemId(Uri itemUri) {
            return Long.parseLong(itemUri.getPathSegments().get(1));
        }


        public static final String TABLE_NAME = "news_table";

        public static final String COLUMN_ID = "news_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PUB_DATE = "pub_date";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_PHOTO_URL = "photo_url";

        public static final String DEFAULT_SORT = COLUMN_PUB_DATE + " DESC";
    }
}
