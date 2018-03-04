package app.services.helloworld

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Component
@ConfigurationProperties(prefix = "services.helloWorld")
class HelloWorldController {

    lateinit var endpoint : String
    val service : HelloWorldService by lazy {
        Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HelloWorldService::class.java)
    }

    fun hello(): String {
        val content = service.hello().execute().body()
        return content?.message ?: "Error"
    }
}

