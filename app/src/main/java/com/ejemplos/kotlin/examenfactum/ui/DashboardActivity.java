package com.ejemplos.kotlin.examenfactum.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ejemplos.kotlin.examenfactum.R;
import com.ejemplos.kotlin.examenfactum.common.Constantes;
import com.ejemplos.kotlin.examenfactum.common.ViewPagerAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {
    TabLayout tlDashboard;                  //elemento con las pestañas a mostrar
    ViewPager vpDashboard;                  //elemento padre con los fragmentos a cargar
    ViewPagerAdapter viewPagerAdapter;      //elemento para configurar los fragmentos a visualizar
    //obtener ubicación del usuario
    private int tiemposeg;
    private int tiempomin;
    private FusedLocationProviderClient fusedLocationClient;

    DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dashboard );

        configView();
        configViewPagerAdapter();
    }
    //método para configurar los elementos de la vista
    private void configView() {
        tlDashboard = findViewById( R.id.tl_Dashboard );
        vpDashboard = findViewById( R.id.vp_Dashboard );
        //crea una instancia de la clase ViewPagerAdapter
        viewPagerAdapter = new ViewPagerAdapter( getSupportFragmentManager() );
        //inicializa el FusedLocation
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //inicializa el acceso a Database
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        //creación del hilo para cronometrar el tiempo
        new Thread( new Runnable() {
            @Override
            public void run() {
                for (;;){
                    try {
                        Thread.sleep( 1000 );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tiemposeg++;
                    Log.d( "DashboardActivity", "Ya pasaron " + tiemposeg + " segundos");
                    if (tiemposeg%60 == 0)
                    {
                        tiemposeg=0;            //reinicia los segundos
                        tiempomin++;            //incrementa los minutos transcurridos
                        runOnUiThread( new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText( getApplicationContext(),"Ya paso " + tiempomin + " minutos",Toast.LENGTH_LONG ).show();
                            }
                        } );
                    }
                }
            }
        } ).start();

        obtenerUbicacion();
    }

    //método para configurar los fragmentos del swipeView
    private void configViewPagerAdapter() {
        //agrega los fragmentos a mostrar
        viewPagerAdapter.addFragment( new MovieFragment(), getResources().getString( R.string.titulo_movies ) );
        viewPagerAdapter.addFragment( new MapaFragment(), getResources().getString( R.string.titulo_mapa ) );
        viewPagerAdapter.addFragment( new ImagenesFragment(), getResources().getString( R.string.titulo_imagenes ) );
        //carga los fragmentos en la actividad principal
        vpDashboard.setAdapter( viewPagerAdapter );
        //relaciona los fragmentos con el tabLayout
        tlDashboard.setupWithViewPager( vpDashboard );
    }

    //método para tomar la última ubicación y mandarla a CloudFirestone
    @SuppressLint("MissingPermission")
    private void obtenerUbicacion() {
        //valida si la aplicación tiene los permisos de ubicación
        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            //sino se tienen los permisos, los solicita en tiempo de ejecución
            ActivityCompat.requestPermissions( this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constantes.SOLICITUD_PERMISOS );

            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Log.e("DashboardActivity" ,"Latitud: " + location.getLatitude() + "Longitud: " + location.getLongitude() );
                            //Mapea para tener la longitud y latitud dentro de un mismo elemento
                            Map<String,Object> latlng = new HashMap<>();        //se inicia vacío
                            latlng.put( "latitud", location.getLatitude() );        //se coloca la latitud
                            latlng.put( "longitud", location.getLongitude() );      //se coloca la longitud
                            //crea un nuevo elemento dentro de Database
                            firebaseDatabase.child( "usuarios" ).push().setValue( latlng );
                        }
                    }
                });
    }
}