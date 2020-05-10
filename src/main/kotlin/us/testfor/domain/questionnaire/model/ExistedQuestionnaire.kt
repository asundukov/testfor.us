package us.testfor.domain.questionnaire.model

open class ExistedQuestionnaire(entity: QuestionnaireEntity): Questionnaire {
    val id: Int = entity.questionnaireId
    override val title = entity.title
    override val description = entity.description
    val createdAt = entity.createdAt
    val committedAt = entity.committedAt
    override val openStat = entity.openStat
    override val openResult = entity.openResult
    override val publishType = PublishType.typeById(entity.publishTypeId)
}
