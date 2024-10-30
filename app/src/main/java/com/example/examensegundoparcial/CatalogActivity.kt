package com.example.examensegundoparcial

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File

class CatalogActivity : AppCompatActivity() {

    private lateinit var etModelo: EditText
    private lateinit var etColor: EditText
    private lateinit var etTamaño: EditText
    private lateinit var etPrecio: EditText
    private lateinit var imgBicicleta: ImageView

    private var imagenUri: Uri? = null
    private val bicicletas = mutableListOf<Bicicleta>()

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            imgBicicleta.setImageURI(imagenUri)
            Toast.makeText(this, "Imagen capturada.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al capturar la imagen.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        etModelo = findViewById(R.id.etModelo)
        etColor = findViewById(R.id.etColor)
        etTamaño = findViewById(R.id.etTamaño)
        etPrecio = findViewById(R.id.etPrecio)
        imgBicicleta = findViewById(R.id.imgBicicleta)

        val btnCapturarImagen: Button = findViewById(R.id.btnCapturarImagen)
        val btnGuardar: Button = findViewById(R.id.btnGuardar)
        val btnBuscar: Button = findViewById(R.id.btnBuscar)

        btnCapturarImagen.setOnClickListener {
            capturarImagen()
        }

        btnGuardar.setOnClickListener {
            guardarBicicleta()
        }

        btnBuscar.setOnClickListener {
            buscarBicicleta()
        }
    }

    private fun capturarImagen() {
        val imagenArchivo = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "bicicleta_${System.currentTimeMillis()}.jpg")
        imagenUri = FileProvider.getUriForFile(this, "com.example.examensegundoparcial.fileprovider", imagenArchivo)
        takePicture.launch(imagenUri)
    }

    private fun guardarBicicleta() {
        val modelo = etModelo.text.toString()
        val color = etColor.text.toString()
        val tamaño = etTamaño.text.toString()
        val precio = etPrecio.text.toString().toDoubleOrNull()

        if (modelo.isEmpty() || color.isEmpty() || tamaño.isEmpty() || precio == null || imagenUri == null) {
            Toast.makeText(this, "Por favor, complete todos los campos y capture una imagen.", Toast.LENGTH_SHORT).show()
            return
        }

        val bicicleta = Bicicleta(modelo, color, tamaño, precio, imagenUri)
        bicicletas.add(bicicleta)
        Toast.makeText(this, "Bicicleta guardada.", Toast.LENGTH_SHORT).show()
        limpiarFormulario()
    }

    private fun buscarBicicleta() {
        val modeloBuscado = etModelo.text.toString()
        val bicicletaEncontrada = bicicletas.find { it.modelo == modeloBuscado }
        Log.d("bicicletas", "tamanio: ${bicicletas.size}")
        if (bicicletaEncontrada != null) {
            etColor.setText(bicicletaEncontrada.color)
            etTamaño.setText(bicicletaEncontrada.tamanio)
            etPrecio.setText(bicicletaEncontrada.precio.toString())
            imgBicicleta.setImageURI(bicicletaEncontrada.imagenUri)
            Toast.makeText(this, "Bicicleta encontrada.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Bicicleta no encontrada.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarFormulario() {
        etModelo.text.clear()
        etColor.text.clear()
        etTamaño.text.clear()
        etPrecio.text.clear()
        imgBicicleta.setImageURI(null)
        imagenUri = null
    }
}