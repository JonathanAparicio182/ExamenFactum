package com.ejemplos.kotlin.examenfactum.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ejemplos.kotlin.examenfactum.R
import com.ejemplos.kotlin.examenfactum.databinding.FragmentImagenesBinding

class ImagenesFragment : Fragment() {
    private lateinit var binding: FragmentImagenesBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentImagenesBinding>(inflater,R.layout.fragment_imagenes,container,false)

        //configView()

        return binding.root
    }
    //función para configurar los elementos del fragmento
    private fun configView() {

    }
}