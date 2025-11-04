package dev.forsythe.patientmanagement.core.data.remote

import dev.forsythe.patientmanagement.utils.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient(
    baseUrl : String = BASE_URL
){
    val client = HttpClient(OkHttp) {

        install(Logging  ){
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

       install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }

    }
}

