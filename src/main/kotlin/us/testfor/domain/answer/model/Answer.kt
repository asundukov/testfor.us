package us.testfor.domain.answer.model

interface Answer {
    val questionId: Int
    val selectedOptions: List<AnswerOption>
}