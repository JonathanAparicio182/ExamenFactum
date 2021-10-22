package com.ejemplos.kotlin.examenfactum.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ejemplos.kotlin.examenfactum.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragment extends Fragment {
    GoogleMap mapa;
    //callback para controlar los eventos y las interacciones en el mapa
    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mapa = googleMap;
            LatLng sydney = new LatLng( -34, 151 );
            mapa.addMarker( new MarkerOptions()
                    .position( sydney )
                    .title( "Marker in Sydney" )
                    //cambia el color del ícono a mostrar
                    .icon( BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE) ) );
            mapa.moveCamera( CameraUpdateFactory.newLatLng( sydney ) );
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate( R.layout.fragment_mapa, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        //permite que el fragmento administre el elemento de GoogleMap
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById( R.id.map );
        if (mapFragment != null) {
            mapFragment.getMapAsync( callback );        //habilita el callback para manipular el mapa
        }
    }
}