package com.nisagurbulak.pokedexcompose.presentation.pokemondetail.components


import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.nisagurbulak.pokedexcompose.common.Resource
import com.nisagurbulak.pokedexcompose.data.remote.responses.Pokemon


@Composable
fun PokemonDetailStateWrapper(
    pokemonInfo: State<Resource<Pokemon>>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
    ) {
    when(pokemonInfo) {

        is Resource.Success<*> -> {

        }
        is Resource.Error<*> -> {

            Text(
                text = pokemonInfo.message!!,
                color = Color.Red,
                modifier = modifier
            )
        }
        is Resource.Loading<*> ->  {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
    }



}