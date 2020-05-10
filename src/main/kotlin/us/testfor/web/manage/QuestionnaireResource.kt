package us.testfor.web.manage

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import us.testfor.service.QuestionnaireService
import us.testfor.web.manage.QuestionnaireResource.Companion.QUESTIONNAIRES_PATH
import us.testfor.web.manage.model.CreateQuestionnaireRequest
import us.testfor.web.manage.model.GetQuestionnaireResponse

@RestController
@RequestMapping(
        path = [QUESTIONNAIRES_PATH],
        produces = [APPLICATION_JSON_VALUE]
)
class QuestionnaireResource(
        val service: QuestionnaireService
) {
    companion object {
        const val QUESTIONNAIRES_PATH = "/api/manage/questionnaires"
        const val QUESTIONNAIRE_PARAM = "questionnaireId"
        const val QUESTIONNAIRE_PATH_PARAM = "{$QUESTIONNAIRE_PARAM}"
        const val QUESTIONNAIRE_PATH = "$QUESTIONNAIRES_PATH/$QUESTIONNAIRE_PATH_PARAM"
    }

    @PostMapping(
            consumes = [APPLICATION_JSON_VALUE]
    )
    fun create(@RequestBody q: CreateQuestionnaireRequest): GetQuestionnaireResponse {
        return GetQuestionnaireResponse(service.save(q.getCreateModel()))
    }

    @GetMapping("/$QUESTIONNAIRE_PATH_PARAM")
    fun get(@PathVariable(QUESTIONNAIRE_PARAM) id: Int): GetQuestionnaireResponse {
        return GetQuestionnaireResponse(service.getById(id))
    }

    @PostMapping("/$QUESTIONNAIRE_PATH_PARAM/commit")
    fun publish(@PathVariable(QUESTIONNAIRE_PARAM) id: Int) {
        service.commit(id)
    }
}
