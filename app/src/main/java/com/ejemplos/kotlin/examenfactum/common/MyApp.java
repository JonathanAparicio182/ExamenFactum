package com.ejemplos.kotlin.examenfactum.common;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    private static MyApp instance;

    public static MyApp getInstance()
    {
        return instance;
    }
    //método para recuperar el contexto de la aplicación
    public static Context getContext()
    {
        return instance;
    }
    //método para obtener la instancia de la aplicación una vez al crear la aplicación
    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
