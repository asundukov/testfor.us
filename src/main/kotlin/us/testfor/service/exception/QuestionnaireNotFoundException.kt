package us.testfor.service.exception

import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(NOT_FOUND)
class QuestionnaireNotFoundException(
        val questionnaireId: Int,
        e: Exception
) : RuntimeException("Not found question with id {$questionnaireId}", e)
