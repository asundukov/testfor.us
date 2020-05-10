package us.testfor.web.common.model

import us.testfor.domain.attempt.model.ExistedAttempt

class CreateAttemptResponse(attempt: ExistedAttempt) {
    val id = attempt.id
    val questionnaireId = attempt.questionnaireId
    val isCompleted = attempt.isCompleted
    val token = attempt.token
}