package contract

import app.services.ServiceBuilder
import app.services.helloworld.HelloWorldController
import app.services.helloworld.HelloWorldService
import au.com.dius.pact.consumer.*
import au.com.dius.pact.model.MockProviderConfig
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HelloWorldContractTest {

    @Test
    fun contract() {
        // Setup
        val result = runConsumerTest(pact, MockProviderConfig.createDefault()!!, object : PactTestRun {
            override fun run(mockServer: MockServer) {
                // Injects new URL
                val controller = HelloWorldController(
                        ServiceBuilder().build(
                                mockServer.getUrl(), HelloWorldService::class.java))

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