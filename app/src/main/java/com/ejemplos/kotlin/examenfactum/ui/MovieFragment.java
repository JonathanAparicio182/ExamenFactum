package com.ejemplos.kotlin.examenfactum.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ejemplos.kotlin.examenfactum.R;

public class MovieFragment extends Fragment {

    public MovieFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root =  inflater.inflate( R.layout.fragment_movie, container, false );

        return root;
    }
}