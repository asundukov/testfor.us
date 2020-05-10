package us.testfor.web.manage

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.greaterThan
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED
import org.springframework.http.HttpStatus.OK
import org.springframework.test.context.TestPropertySource
import us.testfor.domain.questionnaire.model.PublishType.PUBLIC
import us.testfor.web.manage.QuestionResource.Companion.QUESTIONS_PATH
import us.testfor.web.manage.QuestionnaireResource.Companion.QUESTIONNAIRES_PATH
import us.testfor.web.manage.QuestionnaireResource.Companion.QUESTIONNAIRE_PATH_PARAM
import us.testfor.web.manage.model.CreateQuestionRequest
import us.testfor.web.manage.model.CreateQuestionnaireRequest
import us.testfor.web.manage.model.GetQuestionTestResponse
import us.testfor.web.manage.model.GetQuestionnaireTestResponse

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(
        locations = ["classpath:application-integration-test.properties"])
class QuestionResourceTest(
        @Autowired private val rest: TestRestTemplate
) {

    @Test
    fun `Create question correct`() {
        val q = CreateQuestionRequest(
                value = "New question 2",
                points = 2,
                isMultipleChoice = true
        )
        val url = getQuestionsUrlOfNewQuestionnaire()
        val r = rest
                .postForEntity(url, q, GetQuestionTestResponse::class.java)
        assertThat(r.statusCode, equalTo(OK));
        val rb = r.body!!
        assertThat(rb.value, equalTo("New question 2"))
        assertThat(rb.points, equalTo(2))
        assertThat(rb.isMultipleChoice, equalTo(true))
    }

    @Test
    fun `Create question and get correct`() {
        val q = CreateQuestionRequest(
                value = "New question 3",
                points = 3,
                isMultipleChoice = true
        )
        val url = getQuestionsUrlOfNewQuestionnaire()
        val r = rest
                .postForEntity(url, q, GetQuestionTestResponse::class.java)
                .body!!
        val r2 = rest
                .postForEntity(getQuestionUrl(url, r.id), q, String::class.java)
        assertThat(r2.statusCode, equalTo(METHOD_NOT_ALLOWED))
    }

    @Test
    fun `Create multiple questions is correct`() {
        val url = getQuestionsUrlOfNewQuestionnaire()
        val q = CreateQuestionRequest(
                value = "New question 2",
                points = 2,
                isMultipleChoice = true
        )
        val r1 = rest
                .postForEntity(url, q, GetQuestionTestResponse::class.java)
                .body!!
        val r2 = rest
                .postForEntity(url, q, GetQuestionTestResponse::class.java)

        assertThat(r2.statusCode, equalTo(OK))
        assertThat(r2.body!!.id, greaterThan(r1.id))
    }

    @Test
    fun `Create question and post by id not allowed`() {
        val q = CreateQuestionRequest(
                value = "New question 3",
                points = 3,
                isMultipleChoice = true
        )
        val url = getQuestionsUrlOfNewQuestionnaire()
        val r = rest
                .postForEntity(url, q, GetQuestionTestResponse::class.java)
                .body!!
        val r2 = rest
                .getForEntity(getQuestionUrl(url, r.id), GetQuestionTestResponse::class.java)
        assertThat(r2.statusCode, equalTo(OK))

        val rb = r2.body!!
        assertThat(rb.value, equalTo("New question 3"))
        assertThat(rb.points, equalTo(3))
        assertThat(rb.isMultipleChoice, equalTo(true))
    }

    @Test
    fun `Create question can have negative points`() {
        val q = CreateQuestionRequest(
                value = "New question -2",
                points = -2,
                isMultipleChoice = true
        )
        val url = getQuestionsUrlOfNewQuestionnaire()
        val r = rest
                .postForEntity(url, q, GetQuestionTestResponse::class.java)
        assertThat(r.statusCode, equalTo(OK))
        assertThat(r.body!!.points, equalTo(-2))
    }

    @Test
    fun `Create question need value`() {
        val q = mapOf(
                "points" to 1,
                "isMultipleChoice" to true
        )
        val url = getQuestionsUrlOfNewQuestionnaire()
        val r = rest
                .postForEntity(url, q, String::class.java)
        assertThat(r.statusCode, equalTo(BAD_REQUEST))
    }

    @Test
    fun `Get not existed question`() {
        val url = getQuestionsUrlOfNewQuestionnaire()
        val r = rest
                .getForEntity(getQuestionUrl(url, Int.MAX_VALUE), String::class.java)
        assertThat(r.statusCode, equalTo(HttpStatus.NOT_FOUND))
    }

    private fun getQuestionsUrlOfNewQuestionnaire(): String {
        return getQuestionsUrlOfNewQuestionnaire(rest)
    }

    companion object {
        internal fun getQuestionsUrlOfNewQuestionnaire(rest: TestRestTemplate): String {
            val q = CreateQuestionnaireRequest(
                    title = "New questionnaire",
                    description = "Questionnaire description",
                    openStat = true,
                    openResult = true,
                    publishType = PUBLIC
            )
            val r = rest
                    .postForEntity(QUESTIONNAIRES_PATH, q, GetQuestionnaireTestResponse::class.java)
                    .body!!

            return QUESTIONS_PATH.replace(QUESTIONNAIRE_PATH_PARAM, r.id.toString())
        }

        private fun getQuestionUrl(questionsUrl: String, id: Int): String {
            return "$questionsUrl/$id"
        }
    }
}
