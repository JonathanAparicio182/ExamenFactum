package com.ejemplos.kotlin.examenfactum.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.ejemplos.kotlin.examenfactum.R;
import com.ejemplos.kotlin.examenfactum.common.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class DashboardActivity extends AppCompatActivity {
    TabLayout tlDashboard;                  //elemento con las pestañas a mostrar
    ViewPager vpDashboard;                  //elemento padre con los fragmentos a cargar
    ViewPagerAdapter viewPagerAdapter;      //elemento para configurar los fragmentos a visualizar

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