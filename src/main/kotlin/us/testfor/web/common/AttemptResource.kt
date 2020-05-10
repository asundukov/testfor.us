package us.testfor.web.common

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import us.testfor.service.AttemptService
import us.testfor.web.common.model.CreateAttemptResponse
import us.testfor.web.common.model.GetAttemptResponse

@RestController
@RequestMapping(
        path = ["/api/public/questionnaires/{questionnaireId}/attempts"],
        produces = [APPLICATION_JSON_VALUE],
        consumes = [APPLICATION_JSON_VALUE]
)
class AttemptResource(
        val service: AttemptService
) {
    @PostMapping
    fun create(@PathVariable questionnaireId: Int): CreateAttemptResponse {
        return CreateAttemptResponse(service.create(questionnaireId))
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): GetAttemptResponse {
        return GetAttemptResponse(service.getById(id))
    }
}
