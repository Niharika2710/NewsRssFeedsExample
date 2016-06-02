package com.example.niharika.newsrssfeedsexample.remoteUtils;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.RemoteException;
import android.text.format.Time;
import android.util.Log;

import com.example.niharika.newsrssfeedsexample.data.NewsContract;
import com.example.niharika.newsrssfeedsexample.remoteUtils.FeedItem;
import com.example.niharika.newsrssfeedsexample.remoteUtils.ReadRss;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niharika on 5/28/2016.
 */
public class UpdaterService extends IntentService {
    private static final String TAG = "UpdaterService";


    public static final String BROADCAST_ACTION_STATE_CHANGE
            = "com.example.niharika.newsrssfeedsexample.intent.action.STATE_CHANGE";
    public static final String EXTRA_REFRESHING
            = "com.example.niharika.newsrssfeedsexample.intent.extra.REFRESHING";

    public UpdaterService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Time time = new Time();
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null || !ni.isConnected()) {
            Log.w(TAG, "Not online, not refreshing.");
            return;
        }

        sendStickyBroadcast(
                new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_REFRESHING, true));

        // Don't even inspect the intent, we only do one thing, and that's fetch content.
        ArrayList<ContentProviderOperation> cpo = new ArrayList<ContentProviderOperation>();

        Uri dirUri = NewsContract.Items.buildDirUri();

        // Delete all items
        cpo.add(ContentProviderOperation.newDelete(dirUri).build());

        //Adding parsed data to our content providers
        ReadRss rss = new ReadRss();
        List<FeedItem> feedItemList = new ArrayList<FeedItem>();
        feedItemList = rss.parse();

        for (int i = 0; i < feedItemList.size(); i++) {
            ContentValues values = new ContentValues();

            values.put(NewsContract.Items.TITLE, feedItemList.get(i).getTitle());
            values.put(NewsContract.Items.BODY, feedItemList.get(i).getDescription());
            values.put(NewsContract.Items.PHOTO_URL, feedItemList.get(i).getImageUrl());
            values.put(NewsContract.Items.PUBLISHED_DATE, feedItemList.get(i).getPubDate());
            cpo.add(ContentProviderOperation.newInsert(dirUri).withValues(values).build());
        }

        try {
            getContentResolver().applyBatch(NewsContract.CONTENT_AUTHORITY, cpo);
        } catch (RemoteException | OperationApplicationException e) {
            e.printStackTrace();
        }

        sendStickyBroadcast(
                new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_REFRESHING, false));

        }

}