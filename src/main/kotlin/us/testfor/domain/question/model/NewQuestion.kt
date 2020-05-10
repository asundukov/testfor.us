package us.testfor.domain.questionnaire.model

data class NewQuestion(
        val questionnaireId: Int,
        override val points: Int,
        override val value: String,
        override val isMultipleChoice: Boolean
): Question
