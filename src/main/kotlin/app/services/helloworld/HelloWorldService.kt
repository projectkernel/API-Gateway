package app.services.helloworld

import retrofit2.Call
import retrofit2.http.GET

interface HelloWorldService {

    @GET("hello")
    fun hello() : Call<Content>
}