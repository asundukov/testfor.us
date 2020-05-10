package us.testfor.domain.questionnaire.model

interface Questionnaire {
    val title: String
    val description: String
    val openStat: Boolean
    val openResult: Boolean
    val publishType: PublishType
}
