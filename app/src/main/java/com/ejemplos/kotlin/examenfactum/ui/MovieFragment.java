package com.ejemplos.kotlin.examenfactum.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ejemplos.kotlin.examenfactum.R;
import com.ejemplos.kotlin.examenfactum.common.MyApp;
import com.ejemplos.kotlin.examenfactum.common.adaptadores.AdaptadorPeliculas;
import com.ejemplos.kotlin.examenfactum.database.local.MovieEntity;
import com.ejemplos.kotlin.examenfactum.database.network.Resource;
import com.ejemplos.kotlin.examenfactum.viewmodel.MovieViewModel;

import java.util.List;

public class MovieFragment extends Fragment {
    private MovieViewModel movieViewModel;              //objeto con el VM de las películas
    private RecyclerView rvPeliculas;                   //elemento con las películas
    List<MovieEntity> listaPeliculas;                   //lista con las películas a mostrar en la view
    AdaptadorPeliculas adaptadorPeliculas;              //adaptador con la forma y función de cada elemento
    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //instancia del ViewModel
        movieViewModel = ViewModelProviders.of( getActivity() ).get( MovieViewModel.class );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root =  inflater.inflate( R.layout.fragment_movie, container, false );

        rvPeliculas = root.findViewById( R.id.rv_Peliculas );
        //configura la forma en que se mostrara la información en el RecyclerView
        rvPeliculas.setLayoutManager( new GridLayoutManager( MyApp.getContext(),2 ) );
        //crea un adaptador con la forma de la lista
        adaptadorPeliculas = new AdaptadorPeliculas( listaPeliculas,getContext() );
        //relaciona la vista de la lista con el RecyclerView para comenzar a cargar los datos
        rvPeliculas.setAdapter( adaptadorPeliculas );
        //carga las películas en el recycler mediante un observador que detecta los cambios
        movieViewModel.getPopularMovies().observe( getActivity(), new Observer<Resource<List<MovieEntity>>>() {
            @Override
            public void onChanged(Resource<List<MovieEntity>> listResource) {
                listaPeliculas = listResource.data;
                adaptadorPeliculas.setDatos( listaPeliculas );
            }
        } );

        return root;
    }
}