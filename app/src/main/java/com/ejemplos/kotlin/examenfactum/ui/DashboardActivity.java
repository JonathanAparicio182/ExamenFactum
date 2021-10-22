package com.ejemplos.kotlin.examenfactum.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ejemplos.kotlin.examenfactum.R;
import com.ejemplos.kotlin.examenfactum.common.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class DashboardActivity extends AppCompatActivity {
    TabLayout tlDashboard;                  //elemento con las pestañas a mostrar
    ViewPager vpDashboard;                  //elemento padre con los fragmentos a cargar
    ViewPagerAdapter viewPagerAdapter;      //elemento para configurar los fragmentos a visualizar
    private int tiemposeg;
    private int tiempomin;

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
}