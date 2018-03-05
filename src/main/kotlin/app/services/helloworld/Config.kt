package app.services.helloworld

import app.services.ServiceBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "services.helloWorld")
class Config @Autowired constructor(
        var endpoint: String = "",
        val serviceBuilder: ServiceBuilder
) {

    @Bean
    fun helloWorldService() : HelloWorldService {
        return serviceBuilder.build(endpoint,
                        HelloWorldService::class.java)

    }
}