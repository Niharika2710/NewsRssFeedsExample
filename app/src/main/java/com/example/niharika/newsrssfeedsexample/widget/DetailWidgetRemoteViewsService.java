package com.example.niharika.newsrssfeedsexample.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.niharika.newsrssfeedsexample.data.NewsContract;
import com.example.niharika.newsrssfeedsexample.data.NewsProvider.Tables;
import com.example.niharika.newsrssfeedsexample.R;

/**
 * Created by Niharika on 6/1/2016.
 */
public class DetailWidgetRemoteViewsService extends RemoteViewsService {


    public final String LOG_TAG = DetailWidgetRemoteViewsService.class.getSimpleName();

    private static final String[] NEWS_COLUMNS = {
            Tables.ITEMS + "." + NewsContract.Items._ID,
            NewsContract.Items.TITLE,
            NewsContract.Items.BODY,
            NewsContract.Items.PHOTO_URL,
            NewsContract.Items.PUBLISHED_DATE

    };
    // these indices must match the projection
    static final int INDEX_ID = 0;
    static final int INDEX_TITLE = 1;
    static final int INDEX_BODY = 2;
    static final int INDEX_PHOTO_URL = 3;
    static final int INDEX_PUBLISHED_DATE = 4;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission
                final long identityToken = Binder.clearCallingIdentity();
                data = getContentResolver().query(NewsContract.Items.buildItemUri(INDEX_ID),
                        NEWS_COLUMNS,
                        null,
                        null,
                        NewsContract.Items.PUBLISHED_DATE + " ASC");
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }

                // Get the layout
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_collection_item);
                String newsTitle = data.getString(INDEX_TITLE);

                // Bind data to the view
                views.setTextViewText(R.id.title, newsTitle);

                final Intent fillInIntent = new Intent();
                views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);
                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_collection_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(INDEX_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}