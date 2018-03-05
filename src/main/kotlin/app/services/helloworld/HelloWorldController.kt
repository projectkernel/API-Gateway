package app.services.helloworld

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class HelloWorldController @Autowired constructor(
        val service: HelloWorldService
) {

    fun hello(): String {
        val content = service.hello().execute().body()
        return content?.message ?: "Error"
    }
}

