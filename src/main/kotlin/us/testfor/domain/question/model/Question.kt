package us.testfor.domain.questionnaire.model

interface Question {
    val value: String
    val points: Int
    val isMultipleChoice: Boolean
}
