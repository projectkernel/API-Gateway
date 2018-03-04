package contract

import app.services.helloworld.HelloWorldController
import config.UnitConfig
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [UnitConfig::class])
class HelloWorldTest {

    @Autowired
    lateinit var controller : HelloWorldController

    @Test
    fun hello() {
        Assert.assertEquals("Hello World", controller.hello())
    }
}