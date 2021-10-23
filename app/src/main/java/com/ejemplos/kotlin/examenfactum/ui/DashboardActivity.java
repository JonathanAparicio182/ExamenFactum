package com.ejemplos.kotlin.examenfactum.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ejemplos.kotlin.examenfactum.R;
import com.ejemplos.kotlin.examenfactum.common.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

public class DashboardActivity extends AppCompatActivity implements PermissionListener {
    TabLayout tlDashboard;                  //elemento con las pestañas a mostrar
    ViewPager vpDashboard;                  //elemento padre con los fragmentos a cargar
    ViewPagerAdapter viewPagerAdapter;      //elemento para configurar los fragmentos a visualizar
    private int tiemposeg;
    private int tiempomin;
    //variables para obtener permisos de ubicación
    PermissionListener allPermissionsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dashboard );

        configView();
        configViewPagerAdapter();
        checkPermissions();
    }
    //método para configurar los elementos de la vista
    private void configView() {
        tlDashboard = findViewById( R.id.tl_Dashboard );
        vpDashboard = findViewById( R.id.vp_Dashboard );
        //crea una instancia de la clase ViewPagerAdapter
        viewPagerAdapter = new ViewPagerAdapter( getSupportFragmentManager() );
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
    //método para pedir permisos para la ubicación
    private void checkPermissions(){
        PermissionListener dialogOnDenniedPermissionListener =
                DialogOnDeniedPermissionListener.Builder.withContext( getApplicationContext() )
                .withTitle( "Permisos necesarios" )
                .withMessage( "Los permisos son necesarios para poder guardar tu ubicación actual." )
                .withButtonText( "Aceptar" )
                .build();
        //muestra la advertencia al usuario
        allPermissionsListener = new CompositePermissionListener( dialogOnDenniedPermissionListener );
        //se hace la solicitud de los permisos necesarios
        Dexter.withContext( getApplicationContext() ).withPermission( Manifest.permission.ACCESS_FINE_LOCATION )
                .withListener( allPermissionsListener )
                .check();
    }
    //acciones a realizar si se aceptan los permisos
    @Override
    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

    }
    //acciones a realizar si no se aceptan los permisos
    @Override
    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

    }
}