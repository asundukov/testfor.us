package us.testfor.web.common

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import us.testfor.service.AnswerService
import us.testfor.web.common.model.CreateAnswerRequest
import us.testfor.web.common.model.GetAnswerResponse
import javax.validation.Valid

@RestController
@RequestMapping(
        path = ["/api/public/questionnaires/{questionnaireId}/attempts/{attemptId}/answers"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
)
class AnswerResource(
        val service: AnswerService
) {
    @PostMapping
    fun answer(
            @PathVariable questionnaireId: Int,
            @PathVariable attemptId: Int,
            @Valid @RequestBody newAnswer: CreateAnswerRequest
    ): GetAnswerResponse {
        return GetAnswerResponse(service.answer(newAnswer.getCreateModel(attemptId)))
    }

    @GetMapping()
    fun get(
            @PathVariable questionnaireId: Int,
            @PathVariable attemptId: Int
    ): List<GetAnswerResponse> {
        return service.getByQuestionAndAttemptId(attemptId).map { item -> GetAnswerResponse(item) }
    }
}
