package us.testfor.service.exception

import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(CONFLICT)
class QuestionnaireNotEnoughOptionsCommitConflict(val id: Int, val questionId: Int)
    : RuntimeException("Question {$questionId} doesn't have options to commit")
