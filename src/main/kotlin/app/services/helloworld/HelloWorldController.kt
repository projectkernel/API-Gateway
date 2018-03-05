package app.services.helloworld

import app.services.ServiceBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class HelloWorldController @Autowired constructor(
        serviceBuilder: ServiceBuilder,
        config: Config
) {

    private val service =
            serviceBuilder.build(config.endpoint,
                    HelloWorldService::class.java)

    fun hello(): String {
        val content = service.hello().execute().body()
        return content?.message ?: "Error"
    }
}

