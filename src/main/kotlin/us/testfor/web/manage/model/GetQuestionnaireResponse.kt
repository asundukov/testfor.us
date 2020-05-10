package us.testfor.web.manage.model

import us.testfor.domain.questionnaire.model.ExistedQuestionnaire

class GetQuestionnaireResponse(q: ExistedQuestionnaire) {
    val id = q.id
    val title = q.title
    val description = q.description
    val createdAt = q.createdAt
    val openStat = q.openStat
    val openResult = q.openResult
    val publishType = q.publishType
    val committedAt = q.committedAt
}
