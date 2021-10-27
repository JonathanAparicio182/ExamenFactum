package com.ejemplos.kotlin.examenfactum.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ejemplos.kotlin.examenfactum.R;
import com.ejemplos.kotlin.examenfactum.common.MapsPojo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapaFragment extends Fragment {
    GoogleMap mapa;
    DatabaseReference firebaseDatabase;                 //elemento con la instancia de la BD en Firebase
    Double latitud,lontigud;
    //variable con los marcadores a posicionar en el mapa
    ArrayList<Marker> realTimeMarkers = new ArrayList<>();
    //variable con las coordenadas de posición
    ArrayList<LatLng> coordenadas = new ArrayList<>();
    //callback para controlar los eventos y las interacciones en el mapa
    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mapa = googleMap;
            //se ejecuta un escuchador de los cambios en la BD
            firebaseDatabase.child( "usuarios" ).addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //se borran todos los marcadores existentes en el mapa
                    for (Marker marca : realTimeMarkers){
                        marca.remove();
                    }
                    realTimeMarkers.clear();                    //borra todos los marcadores
                    coordenadas.clear();                        //y todas las coordenadas
                    //se obtienen todos los nodos de la BD
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        MapsPojo mp = dataSnapshot.getValue(MapsPojo.class);
                        latitud = mp.getLatitud();
                        lontigud = mp.getLongitud();
                        coordenadas.add( new LatLng( latitud,lontigud ) );
                        //crea el marcador de la posición
                        MarkerOptions marcador = new MarkerOptions();
                        //y configura la posición y el ícono a mostrar
                        marcador.position( new LatLng( latitud,lontigud ) )
                                .icon( BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE) );
                        //dibuja el marcador en el mapa a la vez que almacena el marcador en el ArrayList
                        realTimeMarkers.add( mapa.addMarker( marcador ) );
                        //posiciona la camara en el último marcador colocado
                    }
                    //Toast.makeText( getContext(), "Mapa actualizado", Toast.LENGTH_SHORT ).show();
                    //coloca la cámara en el último marcador colocado con un zoom
                    mapa.moveCamera( CameraUpdateFactory.newLatLngZoom( coordenadas.get( coordenadas.size()-1 ),15f ) );
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );
//            LatLng sydney = new LatLng( -34, 151 );
//            mapa.addMarker( new MarkerOptions()
//                    .position( sydney )
//                    .title( "Marker in Sydney" )
//                    //cambia el color del ícono a mostrar
//                    .icon( BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE) ) );
//            mapa.moveCamera( CameraUpdateFactory.newLatLng( sydney ) );
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate( R.layout.fragment_mapa, container, false );

        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        return root;
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