package us.testfor.web.manage

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import us.testfor.service.QuestionService
import us.testfor.web.manage.QuestionResource.Companion.QUESTIONS_PATH
import us.testfor.web.manage.QuestionnaireResource.Companion.QUESTIONNAIRE_PARAM
import us.testfor.web.manage.QuestionnaireResource.Companion.QUESTIONNAIRE_PATH
import us.testfor.web.manage.model.CreateQuestionRequest
import us.testfor.web.manage.model.GetQuestionResponse

@RestController
@RequestMapping(
        path = [QUESTIONS_PATH],
        produces = [APPLICATION_JSON_VALUE]
)
class QuestionResource(
        val service: QuestionService
) {
    companion object {
        const val QUESTIONS_PATH = "$QUESTIONNAIRE_PATH/questions"
        const val QUESTION_PARAM = "questionId"
        const val QUESTION_PATH_PARAM = "{$QUESTION_PARAM}"
        const val QUESTION_PATH = "$QUESTIONS_PATH/$QUESTION_PATH_PARAM"
    }

    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    fun create(
            @PathVariable(QUESTIONNAIRE_PARAM) questionnaireId: Int,
            @RequestBody q: CreateQuestionRequest
    ): GetQuestionResponse {
        return GetQuestionResponse(service.save(q.getCreateModel(questionnaireId)))
    }

    @GetMapping("/$QUESTION_PATH_PARAM")
    fun get(@PathVariable(QUESTION_PARAM) id: Int): GetQuestionResponse {
        return GetQuestionResponse(service.getById(id))
    }
}
