package us.testfor.web.manage

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED
import org.springframework.test.context.TestPropertySource
import us.testfor.domain.questionnaire.model.PublishType.PRIVATE
import us.testfor.domain.questionnaire.model.PublishType.PUBLIC
import us.testfor.web.manage.QuestionnaireResource.Companion.QUESTIONNAIRES_PATH
import us.testfor.web.manage.QuestionnaireResource.Companion.QUESTIONNAIRE_PATH
import us.testfor.web.manage.QuestionnaireResource.Companion.QUESTIONNAIRE_PATH_PARAM
import us.testfor.web.manage.model.CreateQuestionnaireRequest
import us.testfor.web.manage.model.GetQuestionnaireTestResponse

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(
        locations = ["classpath:application-integration-test.properties"])
class QuestionnaireResourceTest(
        @Autowired private val rest: TestRestTemplate
) {

    @Test
    fun `Create questionnaire correct`() {
        val request = CreateQuestionnaireRequest(
                title = "New questionnaire",
                description = "Questionnaire description",
                openStat = true,
                openResult = true,
                publishType = PUBLIC
        )
        val responseBody = rest
                .postForEntity(QUESTIONNAIRES_PATH, request, GetQuestionnaireTestResponse::class.java)
                .body!!
        assertThat(responseBody.title, equalTo("New questionnaire"))
        assertThat(responseBody.description, equalTo("Questionnaire description"))
        assertThat(responseBody.openResult, equalTo(true))
        assertThat(responseBody.openResult, equalTo(true))
        assertThat(responseBody.publishType, equalTo(PUBLIC))
        assertThat(responseBody.committedAt, nullValue())
    }

    @Test
    fun `Create questionnaire and get correct`() {
        val request = CreateQuestionnaireRequest(
                title = "New questionnaire",
                description = "Questionnaire description",
                openStat = true,
                openResult = true,
                publishType = PRIVATE
        )
        val firstResponseBody = rest
                .postForEntity(QUESTIONNAIRES_PATH, request, GetQuestionnaireTestResponse::class.java)
                .body!!

        val responseBody = rest
                .getForEntity(getQuestionnaireUrl(firstResponseBody.id), GetQuestionnaireTestResponse::class.java)
                .body!!
        assertThat(responseBody.title, equalTo("New questionnaire"))
        assertThat(responseBody.description, equalTo("Questionnaire description"))
        assertThat(responseBody.openResult, equalTo(true))
        assertThat(responseBody.openResult, equalTo(true))
        assertThat(responseBody.publishType, equalTo(PRIVATE))
        assertThat(responseBody.committedAt, nullValue())
    }

    @Test
    fun `Create questionnaire and post by id not is not allowed`() {
        val request = CreateQuestionnaireRequest(
                title = "New questionnaire",
                description = "Questionnaire description",
                openStat = true,
                openResult = true,
                publishType = PUBLIC
        )
        val responseBody = rest
                .postForEntity(QUESTIONNAIRES_PATH, request, GetQuestionnaireTestResponse::class.java)
                .body!!

        val secondResponse = rest
                .postForEntity(getQuestionnaireUrl(responseBody.id), request, String::class.java)
        assertThat(secondResponse.statusCode, equalTo(METHOD_NOT_ALLOWED))
    }

    @Test
    fun `Create questionnaire need title`() {
        val request = mapOf(
                "description" to "d",
                "openResult" to true,
                "openStat" to true
        )
        val response = rest
                .postForEntity(QUESTIONNAIRES_PATH, request, String::class.java)
        assertThat(response.statusCode, equalTo(HttpStatus.BAD_REQUEST))
    }

    @Test
    fun `Create questionnaire need description`() {
        val request = mapOf(
                "title" to "t",
                "openResult" to true,
                "openStat" to true
        )
        val response = rest
                .postForEntity(QUESTIONNAIRES_PATH, request, String::class.java)
        assertThat(response.statusCode, equalTo(HttpStatus.BAD_REQUEST))
    }

    @Test
    fun `Get not existed questionnaire`() {
        val getUrl = QUESTIONNAIRE_PATH.replace(QUESTIONNAIRE_PATH_PARAM, Int.MAX_VALUE.toString())
        val response = rest
                .getForEntity(getUrl, String::class.java)
        assertThat(response.statusCode, equalTo(HttpStatus.NOT_FOUND))
    }

    private fun getQuestionnaireUrl(id: Int): String {
        return QUESTIONNAIRE_PATH.replace(QUESTIONNAIRE_PATH_PARAM, id.toString())
    }

}
