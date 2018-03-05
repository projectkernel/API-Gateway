package app.services.helloworld

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "services.helloWorld")
class Config(var endpoint: String = "")