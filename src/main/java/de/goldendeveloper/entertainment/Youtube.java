package de.goldendeveloper.entertainment;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;

import java.util.Collections;

import com.google.api.services.youtube.model.*;
import io.sentry.Sentry;

import java.util.List;

public class Youtube {

    private String keyWord;
    private YouTube.Search.List search;

    public Youtube(String apiKey) {
        try {
            YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new GsonFactory(), request -> {
            }).setApplicationName("youtube-cmdline-search-sample").build();
            search = youtube.search().list(Collections.singletonList("id,snippet"));
            search.setKey(apiKey);
            search.setType(Collections.singletonList("video"));
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults((long) 25);
        } catch (Exception e) {
            Sentry.captureException(e);
            System.out.println(e.getMessage());
        }
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String execute() {
        try {
            search.setQ(this.keyWord);
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                return prettyPrint(searchResultList);
            }
        } catch (Exception e) {
            Sentry.captureException(e);
            System.out.println(e.getMessage());
        }
        return "";
    }

    private String prettyPrint(List<SearchResult> iteratorSearchResults) {
        if (iteratorSearchResults.isEmpty()) {
            System.out.println("There aren't any results for your query.");
        }
        SearchResult singleVideo = iteratorSearchResults.getFirst();
        if (singleVideo != null) {
            return singleVideo.getId().getVideoId();
        }
        return "";
    }
}
