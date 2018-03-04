package app.graphql

import app.services.helloworld.HelloWorldController
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class QueryResolver : GraphQLQueryResolver {

    @Autowired
    lateinit var helloWorld : HelloWorldController

    fun hello() = helloWorld.hello()
}