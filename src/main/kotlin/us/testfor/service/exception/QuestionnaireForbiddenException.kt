package us.testfor.service.exception

import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(FORBIDDEN)
class QuestionnaireForbiddenException(val id: Int)
    : RuntimeException("You cannot access this questionnaire {$id}")
