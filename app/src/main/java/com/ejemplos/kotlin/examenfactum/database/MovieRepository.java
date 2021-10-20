package com.ejemplos.kotlin.examenfactum.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.ejemplos.kotlin.examenfactum.common.Constantes;
import com.ejemplos.kotlin.examenfactum.common.MyApp;
import com.ejemplos.kotlin.examenfactum.database.local.MovieDao;
import com.ejemplos.kotlin.examenfactum.database.local.MovieDatabase;
import com.ejemplos.kotlin.examenfactum.database.local.MovieEntity;
import com.ejemplos.kotlin.examenfactum.database.network.NetworkBoundResource;
import com.ejemplos.kotlin.examenfactum.database.network.Resource;
import com.ejemplos.kotlin.examenfactum.database.remote.MovieApiService;
import com.ejemplos.kotlin.examenfactum.database.remote.RequestInterceptor;
import com.ejemplos.kotlin.examenfactum.database.remote.model.MoviesResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;

public class MovieRepository {
    private final MovieApiService movieApiService;          //clase con los ENDPOINT para comunicarse con el API
    private final MovieDao movieDao;                        //clase con los ENDPOINT para comunicarse con ROOM
    //definición del constructor de la clase
    public  MovieRepository(){
        //Local > ROOM
        MovieDatabase movieDatabase = Room.databaseBuilder(
                MyApp.getContext(),
                MovieDatabase.class,
                "db_peliculas"
        ).build();
        movieDao = movieDatabase.consultaPeliculas();
        //agrega a la cabecera de la petición el token para autorizar la información
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor( new RequestInterceptor() );
        OkHttpClient cliente = okHttpClientBuilder.build();

        //Remote > Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Constantes.BASE_URL )
                .build();

        movieApiService = retrofit.create( MovieApiService.class );
    }
    //obtención de la lista de películas de acuerdo a la fuente de datos disponible
    public LiveData<Resource<List<MovieEntity>>> getPopularMovies(){
        //tipo que devuelve ROOM, tipo que devuelve el API
        return new NetworkBoundResource<List<MovieEntity>, MoviesResponse>(){
            //respalda la información obtenida de la API en la DB local
            @Override
            protected void saveCallResult(@NonNull MoviesResponse item) {
                movieDao.savePeliculas( item.getResults() );
            }
            //regresa los datos que se tengan en la base de datos local
            @NonNull
            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                return movieDao.loadPeliculas();
            }
            //si se tiene acceso a Internet regresa la información consultada en la API
            @NonNull
            @Override
            protected Call<MoviesResponse> createCall() {
                return movieApiService.loadPopularMovies();
            }
        }.getAsLiveData();
    }
}
