package com.ejemplos.kotlin.examenfactum.database.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface MovieDao {
    //Consultar peliculas almacenadas en cache
    @Query( "SELECT * FROM peliculas" )
    LiveData<List<MovieEntity>> loadPeliculas();
    //Guarda las películas consultadas por si se desconecta la DB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void savePeliculas(List<MovieEntity> movieEntityList);
}
