package com.ejemplos.kotlin.examenfactum.common.adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ejemplos.kotlin.examenfactum.R;
import com.ejemplos.kotlin.examenfactum.common.Constantes;
import com.ejemplos.kotlin.examenfactum.database.local.MovieEntity;

import java.util.List;

public class AdaptadorPeliculas extends RecyclerView.Adapter<AdaptadorPeliculas.ViewHolderPeliculas> {
    private List<MovieEntity> listaPeliculas;           //lista a desplegar en el RecyclerView
    private Context contexto;                           //variable con el contexto del RecyclerView

    public AdaptadorPeliculas(List<MovieEntity> listaPeliculas, Context contexto) {
        this.listaPeliculas = listaPeliculas;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public AdaptadorPeliculas.ViewHolderPeliculas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_peliculas,null, false );
        contexto = vista.getContext();
        return new ViewHolderPeliculas( vista );
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPeliculas.ViewHolderPeliculas holder, int position) {
        if(listaPeliculas != null){                 //si la lista de peliculas no está vacía
            holder.mItem = listaPeliculas.get( position );          //se obtiene la posición del elemento actual
            //y se carga la imagen proveniente de la API MovieDB
            Glide.with( contexto )
                    .load( Constantes.IMAGE_API + holder.mItem.getPosterPath() ).into( holder.ivCover );
        }
        else
            Log.d("Lista Vacía", "La lista de películas esta vacía");
    }
    //Método para actualizar la información del adaptador
    public void setDatos(List<MovieEntity> nuevasPeliculas){
        this.listaPeliculas = nuevasPeliculas;                  //actualiza la lista del adaptador
        notifyDataSetChanged();                                 //y la vuelve a dibujar en la vista
    }
    //Método para saber la cantidad de elementos que tiene el adaptador
    @Override
    public int getItemCount() {
        if (listaPeliculas != null)
            return listaPeliculas.size();
        else
            return 0;
    }

    public class ViewHolderPeliculas extends RecyclerView.ViewHolder{
        ImageView ivCover;
        public MovieEntity mItem;
        public ViewHolderPeliculas(@NonNull View itemView) {
            super( itemView );
            ivCover = itemView.findViewById( R.id.iv_Cover );
        }
    }
}
