package us.testfor.web.manage.model

import com.fasterxml.jackson.annotation.JsonProperty
import us.testfor.domain.option.model.NewOption

class CreateOptionRequest (
        @field:JsonProperty
        private val value: String,
        @field:JsonProperty
        private val points: Int,
        @field:JsonProperty
        private val isRight: Boolean,
        @field:JsonProperty
        private val isCustom: Boolean
) {
    fun getCreateModel(questionnaireId: Int, questionId: Int): NewOption {
        return NewOption(
                questionnaireId = questionnaireId,
                questionId = questionId,
                value = value,
                points = points,
                isRight = isRight,
                isCustom = isCustom
        )
    }
}
