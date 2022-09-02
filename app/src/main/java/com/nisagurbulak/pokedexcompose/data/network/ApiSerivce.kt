package com.nisagurbulak.pokedexcompose.data.network

import com.nisagurbulak.pokedexcompose.data.remote.responses.Pokemon
import com.nisagurbulak.pokedexcompose.data.remote.responses.PokemonList
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class ApiSerivce @Inject constructor(private val client : HttpClient) {

    suspend fun getPokemonList(
         limit : Int,
         offset : Int
    ) : PokemonList = client.get("https://pokeapi.co/api/v2/pokemon") {
        url {
            parameters.append("limit" , limit.toString())
            parameters.append("offset", offset.toString())
        }
    }

    suspend fun getPokemonInfo(
        name : String
    ) : Pokemon = client.get("https://pokeapi.co/api/v2/pokemon/${name}")
}