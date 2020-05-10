package us.testfor.domain.questionnaire.model

import us.testfor.domain.question.model.FullQuestion

class FullQuestionnaire(entity: QuestionnaireEntity): ExistedQuestionnaire(entity) {
    val questions = entity.questions.map { item -> FullQuestion(item) }
}