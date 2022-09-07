package com.nisagurbulak.pokedexcompose.presentation.pokemonlist

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import coil.Coil
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.nisagurbulak.pokedexcompose.common.Constants.PAGE_SIZE
import com.nisagurbulak.pokedexcompose.common.Resource
import com.nisagurbulak.pokedexcompose.data.models.PokedexListEntry
import com.nisagurbulak.pokedexcompose.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var currentPage = 0
    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    //result = Resource<PokemonList> <-- repository.getPokemonList
    // result is success or not ?
    //when(result)  ->> Resource.Success or Resource.Error
    //Loading the results from the api was successful or not
    // result.data!!.count --> actual size of our list
    fun loadPokemonPaginated() {
        viewModelScope.launch {   // launch a coroutine in viewmodel scope because we need to api request here
            isLoading.value = true
            when (val result = repository.getPokemonList(PAGE_SIZE, currentPage * PAGE_SIZE)) {
                is Resource.Success -> {
                    endReached.value = currentPage * PAGE_SIZE >= result.data!!.count // count -->1154 is const!!!

                    // "results":[{"name":"bulbasaur","url":"https://pokeapi.co/api/v2/pokemon/1/"}  // PAGE_SIZE = 1 oldugunda

                    val pokedexEntries = result.data.results.mapIndexed { index, entry ->

                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }

                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokedexListEntry(entry.name.capitalize(Locale.ROOT), url, number.toInt())
                    }

                    currentPage++
                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokedexEntries  // 20 - 20 dolduruyoruz.  // asıl ekrana gelen List
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }


    private fun calculateDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {

        val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bitmap).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))

            }

        }
    }

    fun fetchColors(url: String, context: Context, onCalculated: (Color) -> Unit) : ImageRequest {
        lateinit var request: ImageRequest
        viewModelScope.launch {
            // Requesting the image using coil's ImageRequest
            request = ImageRequest.Builder(context)
                .data(url)
                .crossfade(true)
                .allowHardware(false)
                .build()
            val result = Coil.imageLoader(context).execute(request)

            if (result is SuccessResult) {
                // Save the drawable as a state in order to use it on the composable
                // Converting it to bitmap and using it to calculate the palette
                calculateDominantColor(result.drawable) { color ->
                    onCalculated(color)
                }
            }
        }
        return request
    }

}