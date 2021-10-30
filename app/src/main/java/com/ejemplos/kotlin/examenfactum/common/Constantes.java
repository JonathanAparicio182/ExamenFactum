package com.ejemplos.kotlin.examenfactum.common;

public class Constantes {
    //ENDPOINT para cargar imagenes de la API MovieDB
    public static final String IMAGE_API = "https://image.tmdb.org/t/p/w500/";
    //ENDPOINT para acceder a la API MovieDB
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    //api key para poder comunicarse con el servicio TheMovieDB
    public static final String API_KEY = "d16ba9334e0f109b181a3e4c1a626b49";
    //variable para solicitar permisos en tiempo de ejecución
    public static final int SOLICITUD_PERMISOS = 1000;
    //cantidad de minutos para actualizar la ubicación
    public static final int MINUTOS_ACTUALIZA = 5;
    //variable para solicitar lanzar la cámara
    public static final int LANZAR_CAMARA = 1001;
    //variable para solicitar abrir la galería
    public static final int LANZAR_GALERIA = 1002;
}
