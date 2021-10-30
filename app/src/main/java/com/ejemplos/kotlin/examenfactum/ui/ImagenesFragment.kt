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
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ejemplos.kotlin.examenfactum.R
import com.ejemplos.kotlin.examenfactum.common.Constantes
import com.ejemplos.kotlin.examenfactum.databinding.FragmentImagenesBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.util.*

class ImagenesFragment : Fragment() {
    private lateinit var binding: FragmentImagenesBinding
    private var imageUri : Uri ?= null                  //variable con la ubicación de la imagen(puede ser nula)
    private var storage : FirebaseStorage? = null
    private var selSave = 0                             //variable para saber que se quiere guardar
    private lateinit var imageBitmap: Bitmap
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentImagenesBinding>(inflater,R.layout.fragment_imagenes,container,false)

        configView()

        return binding.root
    }
    //función para configurar los elementos del fragmento
    private fun configView() {
        storage = FirebaseStorage.getInstance()                 //declara una instancia del almacenamiento
        binding.btnFoto.setOnClickListener { takePhoto() }
        binding.btnGaleria.setOnClickListener { selectPhotoGallery() }
        binding.btnGuardar.setOnClickListener { guardarImagen() }
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
    //función para almacenar la imagen en Storege de Firebase
    private fun guardarImagen() {
        when{
            //si se selecciono una imagen almacenada en el dispositivo
            imageUri!= null && selSave==1 -> {
                //selecciona la ubicación donde se quiere guardar la imagen
                val filePath = storage!!.reference!!.child("Fotos/imagen${imageUri!!.lastPathSegment}")
                Toast.makeText(context,"${resources.getString(R.string.subiendoImagen)}",Toast.LENGTH_SHORT).show()
                //sube el archivo al storage
                filePath?.putFile(imageUri!!)!!.addOnSuccessListener { recibido ->
                    //al cargar la imagen manda un mensaje
                    Toast.makeText(context,"${resources.getString(R.string.imagenSubida)}",Toast.LENGTH_SHORT).show()
                    imageUri = null             //vacía el uri para que no se vuelva a subir la misma imagen
                    selSave = 0
                    binding.ivPreview.setImageResource(R.drawable.ic_fotografia)
                }
            }
            //si se quiere subir una foto que no esta guardada en el dispositivo
            selSave == 2 -> {
                //selecciona la ubicación donde se quiere guardar la imagen y su nombre
                val filePath = storage!!.reference!!.child("Fotos/foto${obtenerFecha()}")
                val baos = ByteArrayOutputStream()                  //variable donde se guardara la compresión
                imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)   //comprime la imagen en JPG
                val data = baos.toByteArray()                       //convierte la imagen en un arreglo de bytes
                Toast.makeText(context,"${resources.getString(R.string.subiendoImagen)}",Toast.LENGTH_SHORT).show()
                filePath.putBytes(data).addOnSuccessListener { recibido ->
                    //al cargar la imagen manda un mensaje
                    Toast.makeText(context,"${resources.getString(R.string.imagenSubida)}",Toast.LENGTH_SHORT).show()
                    selSave = 0
                    binding.ivPreview.setImageResource(R.drawable.ic_fotografia)
                }
            }
            //si se presiona guardar pero no hay ninguna imagen seleccionada
            selSave == 0 -> {
                Toast.makeText(context,"${resources.getString(R.string.seleccionaImagen)}",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when{
            //si se tomo una foto correctamente
            requestCode == Constantes.LANZAR_CAMARA && resultCode == RESULT_OK -> {
                data?.extras?.let { bundle ->                           //obtiene la foto y lanza una expresión lambda
                    imageBitmap = bundle.get("data") as Bitmap          //convierte la imagen en un Bitmap
                    binding.ivPreview.setImageBitmap(imageBitmap)       //y la carga en el ImageView
                    selSave = 2
                }
            }
            //si se accedio a la Galería y se selecciono una imagen correctamente
            requestCode == Constantes.LANZAR_GALERIA && resultCode == RESULT_OK -> {
                imageUri = data!!.data                                  //recupera el id de la ubicación de la imagen
                binding.ivPreview.setImageURI(imageUri)                 //y la carga en el ImageView
                selSave = 1
            }
        }
    }
    //función para obtener la hora y fecha actual
    fun obtenerFecha(): String {
        val calendario = Calendar.getInstance()                     //obtiene una instancia del calendario
        //concatena la hora actual
        var horaString = "${calendario.get(Calendar.DAY_OF_MONTH)}${calendario.get(Calendar.MONTH)+1}${calendario.get(Calendar.YEAR)}-" +
                "${calendario.get(Calendar.HOUR_OF_DAY)}:${calendario.get(Calendar.MINUTE)}:${calendario.get(java.util.Calendar.SECOND)}"
        return horaString
    }
}