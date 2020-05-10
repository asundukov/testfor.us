package us.testfor.domain.answer.model

class ExistedAnswer(entity: AnswerEntity): Answer {
    val id = entity.answerId
    override val questionId = entity.question.questionId
    override val selectedOptions = entity.answerOptions.map { item -> ExistedAnswerOption(item) }
}
