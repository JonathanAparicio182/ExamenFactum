package com.ejemplos.kotlin.examenfactum.ui

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ejemplos.kotlin.examenfactum.R
import com.ejemplos.kotlin.examenfactum.common.Constantes
import com.ejemplos.kotlin.examenfactum.databinding.FragmentImagenesBinding

class ImagenesFragment : Fragment() {
    private lateinit var binding: FragmentImagenesBinding
    private var imageUri : Uri ?= null                  //variable con la ubicación de la imagen(puede ser nula)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentImagenesBinding>(inflater,R.layout.fragment_imagenes,container,false)

        configView()

        return binding.root
    }
    //función para configurar los elementos del fragmento
    private fun configView() {
        binding.btnFoto.setOnClickListener { takePhoto() }
        binding.btnGaleria.setOnClickListener { selectPhotoGallery() }
    }
    //función para lanzar la cámara
    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePicture ->
            startActivityForResult(takePicture,Constantes.LANZAR_CAMARA)
        }
    }
    //función para lanzar la galeria
    private fun selectPhotoGallery(){
        Intent(Intent.ACTION_PICK).also { openGallery ->
            openGallery.type = "image/*"                        //se accede a todas las imagenes existentes en el dispositivo
            startActivityForResult(openGallery,Constantes.LANZAR_GALERIA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when{
            //si se tomo una foto correctamente
            requestCode == Constantes.LANZAR_CAMARA && resultCode == RESULT_OK -> {
                data?.extras?.let { bundle ->                           //obtiene la foto y lanza una expresión lambda
                    val imageBitmap = bundle.get("data") as Bitmap      //convierte la imagen en un Bitmap
                    binding.ivPreview.setImageBitmap(imageBitmap)       //y la carga en el ImageView
                }
            }
            //si se accedio a la Galería y se selecciono una imagen correctamente
            requestCode == Constantes.LANZAR_GALERIA && resultCode == RESULT_OK -> {
                imageUri = data!!.data                                  //recupera el id de la ubicación de la imagen
                binding.ivPreview.setImageURI(imageUri)                 //y la carga en el ImageView
            }
        }
    }
}