package app.graphql

import com.coxautodev.graphql.tools.SchemaParser
import graphql.servlet.SimpleGraphQLServlet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class GraphQLEndpoint @Autowired constructor(
        queryResolver: QueryResolver) {

    val servlet = SimpleGraphQLServlet.builder(SchemaParser.newParser()
            .file("schema.graphqls")
            .resolvers(
                    queryResolver
            )
            .build()
            .makeExecutableSchema())
            .build()!!

}