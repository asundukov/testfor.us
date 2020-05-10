package us.testfor.domain.attempt.model

class ExistedAttempt(entity: AttemptEntity) {
    val id = entity.attemptId
    val token = entity.token
    val isCompleted = entity.isCompleted
    val questionnaireId = entity.questionnaire.questionnaireId
}
