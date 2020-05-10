package us.testfor.web.manage.model

import com.fasterxml.jackson.annotation.JsonProperty
import us.testfor.domain.questionnaire.model.NewQuestion

class CreateQuestionRequest (
        @field:JsonProperty
        private val value: String,
        @field:JsonProperty
        private val points: Int,
        @field:JsonProperty
        private val isMultipleChoice: Boolean
) {
    fun getCreateModel(questionnaireId: Int): NewQuestion {
        return NewQuestion(
                questionnaireId = questionnaireId,
                value = value,
                points = points,
                isMultipleChoice = isMultipleChoice
        )
    }
}
