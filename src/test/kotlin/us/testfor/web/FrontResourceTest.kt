package us.testfor.web

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = ["classpath:application-integration-test.properties"])
class FrontResourceTest(
        @Autowired private val rest: TestRestTemplate
) {
    @Test
    fun `Assert main page title contains hello words`() {
        val html = rest.getForEntity("/", String::class.java)
        MatcherAssert.assertThat(html.body, Matchers.containsString("<h1>Hello"))
    }

}