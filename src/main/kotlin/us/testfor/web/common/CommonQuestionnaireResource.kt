package us.testfor.web.common

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import us.testfor.service.QuestionnaireService
import us.testfor.web.common.CommonQuestionnaireResource.Companion.QUESTIONNAIRES_PATH
import us.testfor.web.common.model.PublicQuestionnaireResponse

@RestController
@RequestMapping(
        path = [QUESTIONNAIRES_PATH],
        produces = [APPLICATION_JSON_VALUE]
)
class CommonQuestionnaireResource(
        val service: QuestionnaireService
) {
    companion object {
        const val QUESTIONNAIRES_PATH = "/api/public/questionnaires"
        const val QUESTIONNAIRE_PARAM = "questionnaireId"
        const val QUESTIONNAIRE_PATH_PARAM = "{$QUESTIONNAIRE_PARAM}"
        const val QUESTIONNAIRE_PATH = "$QUESTIONNAIRES_PATH/$QUESTIONNAIRE_PATH_PARAM"
    }

    @GetMapping("/$QUESTIONNAIRE_PATH_PARAM")
    fun get(@PathVariable(QUESTIONNAIRE_PARAM) id: Int): PublicQuestionnaireResponse {
        return PublicQuestionnaireResponse(service.getFullById(id))
    }
}
