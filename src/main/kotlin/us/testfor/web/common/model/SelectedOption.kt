package us.testfor.web.common.model

import us.testfor.domain.answer.model.NewAnswerOption

data class SelectedOption (
    internal val optionId: Int,
    internal val value: String
) {
    fun getNewAnswerOption(): NewAnswerOption {
        return NewAnswerOption(
                value = value,
                optionId = optionId
        )
    }
}
