package contract

import app.services.ServiceBuilder
import app.services.helloworld.HelloWorldController
import app.services.helloworld.HelloWorldService
import au.com.dius.pact.consumer.ConsumerPactBuilder
import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.PactTestRun
import au.com.dius.pact.consumer.runConsumerTest
import au.com.dius.pact.model.MockProviderConfig
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit4.SpringRunner
import java.util.concurrent.CountDownLatch

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [HelloWorldController::class, HelloWorldTest.TestConfig::class])
class HelloWorldTest {


    @TestConfiguration
    class TestConfig {

        @Mock
        lateinit var mockServiceBuilder : ServiceBuilder
        val serviceBuilder = ServiceBuilder()

        @Bean
        fun serviceBuilder() : ServiceBuilder {
            MockitoAnnotations.initMocks(this)
            val countDownLatch = CountDownLatch(1)
            val result = runConsumerTest(pact, mock, object : PactTestRun {
                override fun run(mockServer: MockServer) {
                    // Injects new URL
                    Mockito.`when`(mockServiceBuilder.build(Mockito.anyString(), Mockito.any()))
                            .thenReturn(serviceBuilder.build(mockServer.getUrl(), HelloWorldService::class.java))

                    countDownLatch.countDown()
                }
            })

//            if (result is PactVerificationResult.Error) {
//                throw RuntimeException(result.error)
//            }
//
//            Assert.assertEquals(PactVerificationResult.Ok, result)

            countDownLatch.await()
            return mockServiceBuilder
        }
    }

    @Autowired
    lateinit var serviceBuilder: ServiceBuilder

    @Autowired
    lateinit var controller : HelloWorldController

    @Test
    fun contract() {
        Assert.assertEquals(content, controller.hello())
    }

    companion object {
//        private val controller = HelloWorldController()
        private const val content = "Hello World"
        private val pact = ConsumerPactBuilder("apiGateway")
                .hasPactWith("helloWorld")
                .uponReceiving("a request")
                .path("/hello")
                .method("GET")
                .willRespondWith()
                .body("{\"message\": \"$content\"}")
                .status(200)
                .toPact()!!

        private val mock = MockProviderConfig.createDefault()!!

        @AfterClass
        fun teardown() {
            // Saves pact file on project root
            pact.fileForPact(".")
        }
    }

}