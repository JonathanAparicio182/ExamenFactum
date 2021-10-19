package com.ejemplos.kotlin.examenfactum.database.remote.model;

import com.ejemplos.kotlin.examenfactum.database.local.MovieEntity;

import java.util.List;

public class MoviesResponse {
    private List<MovieEntity> results;

    public List<MovieEntity> getResults() {
        return results;
    }
    public void setResults(List<MovieEntity> results) {
        this.results = results;
    }
}
