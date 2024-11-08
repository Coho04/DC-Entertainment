package io.github.coho04.entertainment;

import com.google.api.client.http.javanet.NetHttpTransport;

import java.util.Collections;

import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import io.sentry.Sentry;

import java.util.List;

/**
 * This class provides methods for interacting with the YouTube API.
 * It contains a YouTube Search.List object for performing searches and a keyword for the search query.
 */
public class Youtube {

    private String keyWord;
    private YouTube.Search.List search;

    /**
     * Constructor for the YouTube class.
     * It initializes the YouTube and Search.List objects and sets the search parameters.
     *
     * @param apiKey The API key for the YouTube API.
     */
    public Youtube(String apiKey) {
        try {

            YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new GsonFactory(), request -> {})
                    .setApplicationName("youtube-cmdline-search-sample")
                    .build();
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

    /**
     * This method sets the keyword for the search query.
     *
     * @param keyWord The keyword for the search query.
     */
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * This method executes the search query and returns the video ID of the first result.
     *
     * @return The video ID of the first result.
     */
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

    /**
     * This method returns the video ID of the first result in the search results.
     * If the search results are empty, it prints a message and returns an empty string.
     *
     * @param iteratorSearchResults The search results.
     * @return The video ID of the first result.
     */
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
