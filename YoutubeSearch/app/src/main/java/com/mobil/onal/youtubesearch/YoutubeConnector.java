package com.mobil.onal.youtubesearch;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onal on 5.01.2018.
 */

public class YoutubeConnector {
    private YouTube mYouTube;
    private YouTube.Search.List mQuery;

    public static final String KEY = "AIzaSyCMkFR_3bD17zMQbaZfl8iFiAxy8uhOBIU";

    //API ye bağlantı sağlıyoruz.
    public YoutubeConnector(Context context) {
        mYouTube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {
            }
        }).setApplicationName(context.getString(R.string.app_name)).build();

        try {
            mQuery = mYouTube.search().list("id,snippet");
            mQuery.setKey(KEY);
            mQuery.setType("video");
            mQuery.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
        } catch (IOException e) {
            Log.d("YC", "Başlatılamadı : " + e);
        }
    }

    //Burda VideoItem class tipinde bir liste oluşturuyoruz ve aşağıda foreach ile title,
    //id, thumbnail, description bilgilerini çekiyoruz. Search ediyoruz.
    public List<VideoItem> search(String keywords){
        mQuery.setQ(keywords);
        try{
            SearchListResponse response = mQuery.execute();
            List<SearchResult> results = response.getItems();

            List<VideoItem> items = new ArrayList<VideoItem>();
            for(SearchResult result:results){
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                item.setId(result.getId().getVideoId());
                items.add(item);
            }
            return items;
        }catch(IOException e){
            Log.d("YC", "Arama yapılamadı.: "+e);
            return null;
        }
    }
}
