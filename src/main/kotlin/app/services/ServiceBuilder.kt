package app.services

import app.services.helloworld.HelloWorldService
import org.springframework.stereotype.Component
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Component
class ServiceBuilder(val s: HelloWorldService? = null) {

    fun build(endpoint : String?, clazz : Class<*>?) : Any {
        if(s != null) {
            return s as HelloWorldService
        }
        return Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(clazz)!!
    }
}