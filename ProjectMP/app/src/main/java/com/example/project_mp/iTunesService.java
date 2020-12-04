package com.example.project_mp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface iTunesService {
    @GET("search")
    Call<RefResponse> getBooks(
            @Query(value="media") String media,
            @Query(value="term") String term
    );
}
