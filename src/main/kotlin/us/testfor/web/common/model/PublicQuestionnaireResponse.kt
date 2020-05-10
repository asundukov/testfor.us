package us.testfor.web.common.model

import us.testfor.domain.questionnaire.model.FullQuestionnaire

class PublicQuestionnaireResponse(q: FullQuestionnaire) {
    val id = q.id
    val title = q.title
    val description = q.description
    val createdAt = q.createdAt
    val openStat = q.openStat
    val openResult = q.openResult
    val questions = q.questions.map {item -> PublicQuestionResponse(item)}
}
