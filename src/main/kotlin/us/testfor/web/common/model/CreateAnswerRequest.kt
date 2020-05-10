package us.testfor.web.common.model

import us.testfor.domain.answer.model.NewAnswer
import javax.validation.constraints.Min


class CreateAnswerRequest(
        @field:Min(1)
        private val questionId: Int,
        private val selectedOptions: List<SelectedOption>

) {
    fun getCreateModel(attemptId: Int): NewAnswer {
        return NewAnswer(
                attemptId = attemptId,
                questionId = questionId,
                selectedOptions = selectedOptions.map {item -> item.getNewAnswerOption()}
        )
    }
}