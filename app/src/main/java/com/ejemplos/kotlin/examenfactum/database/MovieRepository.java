package com.ejemplos.kotlin.examenfactum.database;

import androidx.room.Room;

import com.ejemplos.kotlin.examenfactum.common.Constantes;
import com.ejemplos.kotlin.examenfactum.common.MyApp;
import com.ejemplos.kotlin.examenfactum.database.local.MovieDao;
import com.ejemplos.kotlin.examenfactum.database.local.MovieDatabase;
import com.ejemplos.kotlin.examenfactum.database.remote.MovieApiService;
import com.ejemplos.kotlin.examenfactum.database.remote.RequestInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
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
}
