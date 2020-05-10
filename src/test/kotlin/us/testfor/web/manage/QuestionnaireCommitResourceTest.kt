package us.testfor.web.manage

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.lessThanOrEqualTo
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.test.context.TestPropertySource
import us.testfor.domain.questionnaire.model.PublishType.PUBLIC
import us.testfor.web.manage.QuestionnaireResource.Companion.QUESTIONNAIRES_PATH
import us.testfor.web.manage.model.CreateOptionRequest
import us.testfor.web.manage.model.CreateQuestionRequest
import us.testfor.web.manage.model.CreateQuestionnaireRequest
import us.testfor.web.manage.model.GetOptionTestResponse
import us.testfor.web.manage.model.GetQuestionTestResponse
import us.testfor.web.manage.model.GetQuestionnaireTestResponse
import java.util.Calendar

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(
        locations = ["classpath:application-integration-test.properties"])
class QuestionnaireCommitResourceTest(
        @Autowired private val rest: TestRestTemplate
) {

    @Test
    fun `Commit ok for questionnaire with question and option`() {
        val questionnaireUrl = createAndGetQuestionnaireUrl()
        val questionUrl = createAndGetQuestionUrl(questionnaireUrl)
        createOptionForQuestion(questionUrl)

        val commitResponse = rest.postForEntity("$questionnaireUrl/commit", "", Void::class.java)
        assertThat(commitResponse.statusCode, equalTo(HttpStatus.OK))
    }

    @Test
    fun `Questionnaire has committedAt date after commit`() {
        val questionnaireUrl = createAndGetQuestionnaireUrl()
        val questionUrl = createAndGetQuestionUrl(questionnaireUrl)
        createOptionForQuestion(questionUrl)
        val now = Calendar.getInstance()
        rest.postForEntity("$questionnaireUrl/commit", "", Void::class.java)

        val questionnaireBody = rest
                .getForEntity(questionnaireUrl, GetQuestionnaireTestResponse::class.java)
                .body!!
        assertThat(questionnaireBody.committedAt, notNullValue())
        assertThat(now, lessThanOrEqualTo(questionnaireBody.committedAt))
    }

    @Test
    fun `Questionnaire couldn't committed without option on some questions`() {
        val questionnaireUrl = createAndGetQuestionnaireUrl()
        createAndGetQuestionUrl(questionnaireUrl)
        val commitResponse = rest
                .postForEntity("$questionnaireUrl/commit", "", Void::class.java)

        assertThat(commitResponse.statusCode, equalTo(CONFLICT))
    }

    @Test
    fun `Questionnaire couldn't committed without questions`() {
        val questionnaireUrl = createAndGetQuestionnaireUrl()
        val commitResponse = rest
                .postForEntity("$questionnaireUrl/commit", "", Void::class.java)

        assertThat(commitResponse.statusCode, equalTo(CONFLICT))
    }

    @Test
    fun `Cannot add questions after commit`() {
        val questionnaireUrl = createAndGetQuestionnaireUrl()
        val questionUrl = createAndGetQuestionUrl(questionnaireUrl)
        createOptionForQuestion(questionUrl)
        rest.postForEntity("$questionnaireUrl/commit", "", Void::class.java)


        val questionRequest = CreateQuestionRequest(
                value = "Question 2",
                points = 0,
                isMultipleChoice = false
        )
        val response = rest.postForEntity("$questionnaireUrl/questions", questionRequest, String::class.java)
        assertThat(response.statusCode, equalTo(CONFLICT))
    }

    @Test
    fun `Cannot add options after commit`() {
        val questionnaireUrl = createAndGetQuestionnaireUrl()
        val questionUrl = createAndGetQuestionUrl(questionnaireUrl)
        createOptionForQuestion(questionUrl)

        rest.postForEntity("$questionnaireUrl/commit", "", Void::class.java)

        val optionRequest = CreateOptionRequest(
                value = "option 2",
                isCustom = true,
                isRight = true,
                points = 0
        )
        val response = rest.postForEntity("$questionUrl/options", optionRequest, String::class.java)
        assertThat(response.statusCode, equalTo(CONFLICT))
    }

    private fun createOptionForQuestion(questionUrl: String) {
        val optionsUrl = "$questionUrl/options"
        val optionRequest = CreateOptionRequest(
                value = "option",
                isCustom = true,
                isRight = true,
                points = 0
        )

        rest.postForEntity(optionsUrl, optionRequest, GetOptionTestResponse::class.java)
    }

    private fun createAndGetQuestionUrl(questionnaireUrl: String): String {
        val questionRequest = CreateQuestionRequest(
                value = "Question 1",
                points = 0,
                isMultipleChoice = false
        )
        val questionsUrl = "$questionnaireUrl/questions"
        val questionBody = rest
                .postForEntity(questionsUrl, questionRequest, GetQuestionTestResponse::class.java)
                .body!!

        return questionsUrl + "/" + questionBody.id
    }

    private fun createAndGetQuestionnaireUrl(): String {
        val questionnaireRequest = CreateQuestionnaireRequest(
                title = "New questionnaire",
                description = "Questionnaire description",
                openStat = true,
                openResult = true,
                publishType = PUBLIC
        )
        val questionnaireBody = rest
                .postForEntity(QUESTIONNAIRES_PATH, questionnaireRequest, GetQuestionnaireTestResponse::class.java)
                .body!!

        return QUESTIONNAIRES_PATH + "/" + questionnaireBody.id
    }

}
