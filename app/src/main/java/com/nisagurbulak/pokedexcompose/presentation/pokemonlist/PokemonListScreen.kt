package com.nisagurbulak.pokedexcompose.presentation.pokemonlist


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nisagurbulak.pokedexcompose.R
import androidx.compose.foundation.Image
import androidx.compose.material.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import com.nisagurbulak.pokedexcompose.presentation.pokemonlist.components.PokemonList
import com.nisagurbulak.pokedexcompose.presentation.pokemonlist.components.SearchBar


@Composable
fun PokemonListScreen(navController: NavController) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier =  Modifier.fillMaxSize()
    ){
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = "Pokemon",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )
            SearchBar(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                hint = "Search...") {
                //onsearch method is the last parameter of the composable function
            }
            Spacer(modifier = Modifier.height(16.dp))
            PokemonList(navController = navController)

        }

    }

}













