package com.ejemplos.kotlin.examenfactum.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ejemplos.kotlin.examenfactum.database.MovieRepository;
import com.ejemplos.kotlin.examenfactum.database.local.MovieEntity;
import com.ejemplos.kotlin.examenfactum.database.network.Resource;

import java.util.List;

public class MovieViewModel extends ViewModel {
    private final LiveData<Resource<List<MovieEntity>>> popularMovies;      //objeto con las películas más populares
    private MovieRepository movieRepository;
    //constructor de la clase
    public MovieViewModel() {
        movieRepository = new MovieRepository();
        popularMovies = movieRepository.getPopularMovies();
    }
    //método que devuelve la lista de películas populares
    public LiveData<Resource<List<MovieEntity>>> getPopularMovies(){
        return popularMovies;
    }
}
