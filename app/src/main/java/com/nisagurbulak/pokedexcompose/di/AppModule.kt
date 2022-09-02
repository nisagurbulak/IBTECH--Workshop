package com.nisagurbulak.pokedexcompose.di


import android.util.Log
import com.nisagurbulak.pokedexcompose.data.network.ApiSerivce
import com.nisagurbulak.pokedexcompose.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providePokemonRepository(
        api : ApiSerivce
    ) = PokemonRepository(api)



   @Singleton
   @Provides
    fun provideHttpClient(): HttpClient {

       return HttpClient {
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
           } // time out ' a düşerse. servis çağrısı yaptık request çağrısında 15 sani

           defaultRequest {
               contentType(ContentType.Application.Json)
               accept(ContentType.Application.Json)
           }
       }

    }

}