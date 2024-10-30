package com.example.examensegundoparcial

import android.net.Uri

data class Bicicleta (
    val modelo: String,
    val color: String,
    val tamanio: String,
    val precio: Double,
    val imagenUri: Uri? = null

)