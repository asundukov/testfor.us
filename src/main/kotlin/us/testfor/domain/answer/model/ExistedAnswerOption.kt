package us.testfor.domain.answer.model

class ExistedAnswerOption(entity: AnswerOptionEntity) : AnswerOption() {
    override val optionId = entity.option.optionId
    override val value = entity.value
}