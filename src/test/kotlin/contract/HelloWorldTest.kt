package contract

import app.services.ServiceBuilder
import app.services.helloworld.Config
import app.services.helloworld.HelloWorldController
import app.services.helloworld.HelloWorldService
import au.com.dius.pact.consumer.*
import au.com.dius.pact.model.MockProviderConfig
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HelloWorldTest {

    @Mock
    lateinit var mockServiceBuilder : ServiceBuilder

    @Test
    fun contract() {
        // Setup
        val result = runConsumerTest(pact, MockProviderConfig.createDefault()!!, object : PactTestRun {
            override fun run(mockServer: MockServer) {
                // Injects new URL
                Mockito.doReturn(ServiceBuilder().build(mockServer.getUrl(), HelloWorldService::class.java))
                        .`when`(mockServiceBuilder).build("", HelloWorldService::class.java)
                val controller = HelloWorldController(mockServiceBuilder, Config(""))

                // Assertion
                Assert.assertEquals(content, controller.hello())
            }
        })

        if (result is PactVerificationResult.Error) {
            throw RuntimeException(result.error)
        }
        Assert.assertEquals(PactVerificationResult.Ok, result)
    }

    companion object {
        private const val content = "Hello World"
        // Defines Contract
        private val pact = ConsumerPactBuilder("apiGateway")
                .hasPactWith("helloWorld")
                .uponReceiving("a request")
                .path("/hello")
                .method("GET")
                .willRespondWith()
                .body("{\"message\": \"$content\"}")
                .status(200)
                .toPact()!!


        @AfterClass
        fun teardown() {
            // Saves pact file on project root
            pact.fileForPact(".")
        }
    }

}