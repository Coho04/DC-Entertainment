package de.goldendeveloper.entertainment;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
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
            YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), request -> {
            }).setApplicationName("youtube-cmdline-search-sample").build();
            search = youtube.search().list(Collections.singletonList("id,snippet"));
            search.setKey(apiKey);
            search.setType(Collections.singletonList("video"));
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults((long) 25);
        } catch (Exception e) {
            Sentry.captureException(e);
            e.printStackTrace();
        }
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String execute() {
        try {
            SearchListResponse searchResponse;
            search.setQ(this.keyWord);
            searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                return prettyPrint(searchResultList);
            }
        } catch (Exception e) {
            Sentry.captureException(e);
            e.printStackTrace();
        }
        return "";
    }

    private String prettyPrint(List<SearchResult> iteratorSearchResults) {
        if (iteratorSearchResults.isEmpty()) {
            System.out.println("There aren't any results for your query.");
        }
        SearchResult singleVideo = iteratorSearchResults.get(0);
        if (singleVideo != null) {
            ResourceId rId = singleVideo.getId();
            return rId.getVideoId();
        }
        return "";
    }
}
