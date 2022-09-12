package com.nisagurbulak.pokedexcompose.presentation.pokemondetail


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.nisagurbulak.pokedexcompose.common.Resource
import com.nisagurbulak.pokedexcompose.common.Resource.*
import com.nisagurbulak.pokedexcompose.presentation.pokemondetail.components.PokemonDetailStateWrapper
import com.nisagurbulak.pokedexcompose.presentation.pokemondetail.components.PokemonDetailTopSection
import com.nisagurbulak.pokedexcompose.data.remote.responses.Pokemon as Pokemon

@Composable
fun PokemonDetailScreen (
    pokemonName : String,
    dominantColor : Color,
    navController : NavController,
    topPadding : Dp = 20.dp,
    pokemonImageSize : Dp = 200.dp,
    viewModel : PokemonDetailViewModel = hiltViewModel()
) {
    val pokemonInfo =  produceState<Resource<Pokemon>>(initialValue = Loading())  {
        value = viewModel.getPokemonInfo(pokemonName)
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
            .padding(bottom = 16.dp)

    ) {
        Box(contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
        ) {
            PokemonDetailTopSection(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .align(Alignment.TopCenter)
            )
            PokemonDetailStateWrapper(
                pokemonInfo = pokemonInfo,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 4000.dp / 2f ,
                        bottom = 16.dp ,
                        end = 16.dp,
                        start = 16.dp
                    )
                    .shadow(10.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .padding(16.dp)
                    .background(MaterialTheme.colors.surface)
                    .align(Alignment.BottomCenter)
            )

            pokemonInfo.value.data?.sprites?.let {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(pokemonImageSize)
                        .offset(y = topPadding),
                    model = it.front_default,
                    contentDescription = pokemonInfo.value.data?.name,
                )
            }

        }

    }
}


