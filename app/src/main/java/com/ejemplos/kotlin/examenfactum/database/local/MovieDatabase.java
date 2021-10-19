package com.ejemplos.kotlin.examenfactum.database.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//Clase abstracta para definir la base de datos y sus caracteristicas
@Database( entities = MovieEntity.class, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao consultaPeliculas();
}
