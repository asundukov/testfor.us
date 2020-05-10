package us.testfor.service.exception

import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(CONFLICT)
class QuestionnaireAlreadyCommitted(val id: Int)
    : RuntimeException("Questionnaire {$id} already committed")
