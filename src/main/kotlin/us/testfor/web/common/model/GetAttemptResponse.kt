package us.testfor.web.common.model

import us.testfor.domain.attempt.model.ExistedAttempt

class GetAttemptResponse(attempt: ExistedAttempt) {
    val id = attempt.id
    val questionnaireId = attempt.questionnaireId
    val isCompleted = attempt.isCompleted
}