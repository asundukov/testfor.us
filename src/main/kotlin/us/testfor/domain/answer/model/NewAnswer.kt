package us.testfor.domain.answer.model

class NewAnswer(
        val attemptId: Int,
        override val questionId: Int,
        override val selectedOptions: List<AnswerOption>
): Answer
