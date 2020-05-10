package us.testfor.domain.option.model

data class NewOption(
        val questionnaireId: Int,
        val questionId: Int,
        override val points: Int,
        override val value: String,
        override val isRight: Boolean,
        override val isCustom: Boolean
): Option
