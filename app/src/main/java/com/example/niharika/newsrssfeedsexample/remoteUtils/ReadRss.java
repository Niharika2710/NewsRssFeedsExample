package com.example.niharika.newsrssfeedsexample.remoteUtils;

import android.util.Log;
import android.util.Xml;

import com.example.niharika.newsrssfeedsexample.remoteUtils.FeedItem;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niharika on 5/29/2016.
 */

public class ReadRss {
    private static String link = "http://www.techrepublic.com/mediafed/articles/latest/";

    public List<FeedItem> parse() {
        List<FeedItem> itemsList = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            // auto-detect the encoding from the stream
            parser.setInput(getInputStream(), null);
            int eventType = parser.getEventType();
            FeedItem rssItem = null;
            boolean done = false;
            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                String name = null;
                String attributeValue = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        itemsList = new ArrayList<FeedItem>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();

                        if (name.equalsIgnoreCase("item")) {
                            rssItem = new FeedItem();
                        } else if (rssItem != null) {
                            if (name.equalsIgnoreCase("description")) {
                                rssItem.setDescription(parser.nextText());
                            } else if (name.equalsIgnoreCase("pubDate")) {
                                rssItem.setPubDate(parser.nextText());
                            } else if (name.equalsIgnoreCase("title")) {
                                rssItem.setTitle(parser.nextText());
                            } else if (name.equalsIgnoreCase("enclosure")) {
                                rssItem.setImageUrl(parser.getAttributeValue(null, "url"));
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("item") && rssItem != null) {
                            assert itemsList != null;
                            itemsList.add(rssItem);
                        } else if (name.equalsIgnoreCase("channel")) {
                            done = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            Log.e("AndroidNews:PullFeedParser", e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return itemsList;
    }

    private InputStream getInputStream() {
        try {
            URL url = new URL(link);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            Log.w("TAG", "Exception while retrieving the input stream", e);
            return null;
        }
    }


}


