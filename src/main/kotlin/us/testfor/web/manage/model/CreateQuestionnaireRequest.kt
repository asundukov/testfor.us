package us.testfor.web.manage.model

import com.fasterxml.jackson.annotation.JsonProperty
import us.testfor.domain.questionnaire.model.NewQuestionnaire
import us.testfor.domain.questionnaire.model.PublishType

class CreateQuestionnaireRequest(
        @field:JsonProperty
        private val title: String,
        @field:JsonProperty
        private val description: String,
        @field:JsonProperty
        private val openStat: Boolean,
        @field:JsonProperty
        private val openResult: Boolean,
        @field:JsonProperty
        val publishType: PublishType
) {
    fun getCreateModel(): NewQuestionnaire {
        return NewQuestionnaire(
                title = title,
                description = description,
                openStat = openStat,
                openResult = openResult,
                publishType = publishType
        )
    }
}
