package app.services.helloworld

import app.services.ServiceBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
@ConfigurationProperties(prefix = "services.helloWorld")
class HelloWorldController {

    private lateinit var service : HelloWorldService
    private  var endpoint: String = "http://helloworld.dev.danielspeixoto.com"

    @Autowired
    private lateinit var serviceBuilder: ServiceBuilder

    @PostConstruct
    fun setUp() {
        service = serviceBuilder.build(endpoint, HelloWorldService::class.java) as HelloWorldService
    }

    fun hello(): String {
        val content = service.hello().execute().body()
        return content?.message ?: "Error"
    }
}

