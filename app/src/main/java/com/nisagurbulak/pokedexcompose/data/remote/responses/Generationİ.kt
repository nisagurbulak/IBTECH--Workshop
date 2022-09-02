package com.nisagurbulak.pokedexcompose.data.remote.responses

import com.google.gson.annotations.SerializedName

data class Generationİ(
    @SerializedName("red-blue")
    val redBlue: RedBlue,
    val yellow: Yellow
)