package us.testfor.service.exception

import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(NOT_FOUND)
class QuestionNotFoundException(
        val questionId: Int
) : RuntimeException("Not found question with id {$questionId}")
