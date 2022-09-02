package com.nisagurbulak.pokedexcompose.data.network

import android.util.Log
import com.nisagurbulak.pokedexcompose.data.remote.responses.Pokemon
import com.nisagurbulak.pokedexcompose.data.remote.responses.PokemonList
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*


object KtorClient {



    val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.i("ktor-log", message)
                }
            }
            level = LogLevel.ALL
        }

        install(HttpTimeout) {
            socketTimeoutMillis = 15_000
            requestTimeoutMillis = 15_000
            connectTimeoutMillis = 15_000
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    /*suspend fun getPokemonList(
        limit : Int,
        offset : Int
    ) : PokemonList {
        return httpClient.get {
            url("https://pokeapi.co/api/v2/pokemon")
        }
    } */


    //"https://pokeapi.co/api/v2/pokemon?offset=20&limit=20"
    suspend fun getPokemonList(limit : Int, offset : Int) : PokemonList {
        //offset will be 0 at start
        return httpClient.get("https://pokeapi.co/api/v2/pokemon") {
            url {
                parameters.append("limit" , limit.toString())
                parameters.append("offset", offset.toString())
            }
        }
    }


    /*suspend fun getPokemonInfo(
         name : String
    ) : Pokemon {
        return httpClient.get {
            url("https://pokeapi.co/api/v2/pokemon/{name}")
        }
    } */


    // https://ktor.io/docs/welcome.html
    suspend fun getPokemonInfo(
        name : String
    ) : Pokemon {
        return httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "pokeapi.co/api/v2"
                path("pokemon/{name}")
                //path("pokemon/${name}")
            }
        }
    }

}
