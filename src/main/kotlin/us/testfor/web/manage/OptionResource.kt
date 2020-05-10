package us.testfor.web.manage

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import us.testfor.service.OptionService
import us.testfor.web.manage.QuestionResource.Companion.QUESTION_PARAM
import us.testfor.web.manage.QuestionResource.Companion.QUESTION_PATH
import us.testfor.web.manage.QuestionnaireResource.Companion.QUESTIONNAIRE_PARAM
import us.testfor.web.manage.model.CreateOptionRequest
import us.testfor.web.manage.model.GetOptionResponse

@RestController
@RequestMapping(
        path = ["/api/manage/questionnaires/{questionnaireId}/questions/{questionId}/options"],
        produces = [APPLICATION_JSON_VALUE]
)
class OptionResource(
        val service: OptionService
) {
    companion object {
        const val OPTIONS_PATH = "${QUESTION_PATH}/options"
        const val OPTION_PARAM = "optionId"
        const val OPTION_PATH_PARAM = "{$OPTION_PARAM}"
        const val OPTION_PATH = "$OPTIONS_PATH/$OPTION_PATH_PARAM"
    }

    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    fun create(
            @PathVariable(QUESTIONNAIRE_PARAM) questionnaireId: Int,
            @PathVariable(QUESTION_PARAM) questionId: Int,
            @RequestBody q: CreateOptionRequest
    ): GetOptionResponse {
        return GetOptionResponse(service.save(q.getCreateModel(questionnaireId, questionId)))
    }

    @GetMapping("/$OPTION_PATH_PARAM")
    fun get(
            @PathVariable(QUESTIONNAIRE_PARAM) questionnaireId: Int,
            @PathVariable(QUESTION_PARAM) questionId: Int,
            @PathVariable(OPTION_PARAM) optionId: Int
    ): GetOptionResponse {
        return GetOptionResponse(service.getById(optionId))
    }
}
