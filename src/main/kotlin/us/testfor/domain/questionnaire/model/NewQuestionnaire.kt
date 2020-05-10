package us.testfor.domain.questionnaire.model

data class NewQuestionnaire(
        override val title: String,
        override val description: String,
        override val openStat: Boolean,
        override val openResult: Boolean,
        override val publishType: PublishType
): Questionnaire
