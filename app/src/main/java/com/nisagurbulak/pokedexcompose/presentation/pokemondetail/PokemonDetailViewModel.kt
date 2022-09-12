package com.nisagurbulak.pokedexcompose.presentation.pokemondetail

import androidx.lifecycle.ViewModel
import com.nisagurbulak.pokedexcompose.common.Resource
import com.nisagurbulak.pokedexcompose.data.remote.responses.Pokemon
import com.nisagurbulak.pokedexcompose.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PokemonDetailViewModel  @Inject constructor(
    private val repository : PokemonRepository
): ViewModel() {

    suspend fun getPokemonInfo(pokemonName : String) : Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }


}