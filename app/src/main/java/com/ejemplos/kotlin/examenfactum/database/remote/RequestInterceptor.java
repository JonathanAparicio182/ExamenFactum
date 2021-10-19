package com.ejemplos.kotlin.examenfactum.database.remote;

import com.ejemplos.kotlin.examenfactum.common.Constantes;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //obtiene la cadena que se quiere transmitir
        Request originalRequest = chain.request();
        //obtiene la url
        HttpUrl originalHttpUrl = originalRequest.url();
        //y le agrega el apikey
        HttpUrl url = originalHttpUrl.newBuilder().addQueryParameter( "api_key", Constantes.API_KEY ).build();

        Request request = originalRequest.newBuilder().url( url ).build();      //crea la nueva respuesta

        return chain.proceed( request );
    }
}
