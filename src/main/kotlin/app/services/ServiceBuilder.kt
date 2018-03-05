package app.services

import org.springframework.stereotype.Component
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Component
class ServiceBuilder {

    fun <T> build(endpoint : String, clazz : Class<T>) : T {
        return Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(clazz)!!
    }
}