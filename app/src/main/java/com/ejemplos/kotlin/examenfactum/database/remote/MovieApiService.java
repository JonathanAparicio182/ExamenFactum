package com.ejemplos.kotlin.examenfactum.database.remote;

import com.ejemplos.kotlin.examenfactum.database.remote.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApiService {
    @GET("movie/popular")
    Call<MoviesResponse> loadPopularMovies();
}
