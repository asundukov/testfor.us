package us.testfor.web.manage

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.lessThan
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.test.context.TestPropertySource
import us.testfor.web.manage.QuestionResourceTest.Companion.getQuestionsUrlOfNewQuestionnaire
import us.testfor.web.manage.model.CreateOptionRequest
import us.testfor.web.manage.model.CreateQuestionRequest
import us.testfor.web.manage.model.GetOptionTestResponse
import us.testfor.web.manage.model.GetQuestionTestResponse

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(
        locations = ["classpath:application-integration-test.properties"])
class OptionResourceTest(
        @Autowired private val rest: TestRestTemplate
) {
    val questionsUrl = getQuestionsUrlOfNewQuestionnaire(rest)

    @Test
    fun `Create option correct`() {
        val request = CreateOptionRequest(
                value = "New option 1",
                points = 1,
                isRight = true,
                isCustom = false
        )
        val optionsUrl = getOptionsUrlOfNewQuestion()
        val response = rest
                .postForEntity(optionsUrl, request, GetOptionTestResponse::class.java)
        assertThat(response.statusCode, equalTo(OK));
        val body = response.body!!
        assertThat(body.value, equalTo("New option 1"))
        assertThat(body.points, equalTo(1))
        assertThat(body.isRight, equalTo(true))
        assertThat(body.isCustom, equalTo(false))
    }

    @Test
    fun `Create option and get correct`() {
        val request = CreateOptionRequest(
                value = "New option 2",
                points = 2,
                isRight = false,
                isCustom = true
        )
        val optionsUrl = getOptionsUrlOfNewQuestion()
        val firstResponseBody = rest
                .postForEntity(optionsUrl, request, GetOptionTestResponse::class.java)
                .body!!
        val secondResponseBody = rest
                .getForEntity(getOptionUrl(optionsUrl, firstResponseBody.id), GetOptionTestResponse::class.java)
                .body!!
        assertThat(secondResponseBody.value, equalTo("New option 2"))
        assertThat(secondResponseBody.points, equalTo(2))
        assertThat(secondResponseBody.isRight, equalTo(false))
        assertThat(secondResponseBody.isCustom, equalTo(true))
    }

    @Test
    fun `Create multiple option correct`() {
        val request = CreateOptionRequest(
                value = "New option 3",
                points = 3,
                isRight = true,
                isCustom = false
        )
        val optionsUrl = getOptionsUrlOfNewQuestion()

        val first = rest
                .postForEntity(optionsUrl, request, GetOptionTestResponse::class.java)
        val second = rest
                .postForEntity(optionsUrl, request, GetOptionTestResponse::class.java)

        assertThat(second.statusCode, equalTo(OK))
        assertThat(first.body!!.id, lessThan(second.body!!.id))
    }

    @Test
    fun `Create option need value`() {
        val request = mapOf(
                "points" to 3,
                "isRight" to true,
                "isCustom" to true
        )
        val url = getOptionsUrlOfNewQuestion()
        val response = rest
                .postForEntity(url, request, String::class.java)
        assertThat(response.statusCode, equalTo(HttpStatus.BAD_REQUEST))
    }


    private fun getOptionsUrlOfNewQuestion(): String {
        return getOptionsUrlOfNewQuestion(questionsUrl, rest)
    }

    companion object {
        internal fun getOptionsUrlOfNewQuestion(questionsUrl: String, rest: TestRestTemplate): String {

            val q = CreateQuestionRequest(
                    value = "New question 2",
                    points = 2,
                    isMultipleChoice = true
            )
            val r = rest
                    .postForEntity(questionsUrl, q, GetQuestionTestResponse::class.java)
                    .body!!

            return questionsUrl + "/" + r.id + "/options"
        }

        private fun getOptionUrl(optionsUrl: String, id: Int): String {
            return "$optionsUrl/$id"
        }
    }

}
