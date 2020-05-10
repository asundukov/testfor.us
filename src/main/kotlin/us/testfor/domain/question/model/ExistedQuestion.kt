package us.testfor.domain.question.model

import us.testfor.domain.questionnaire.model.Question
import us.testfor.domain.questionnaire.model.QuestionEntity

open class ExistedQuestion(entity: QuestionEntity): Question {
    val id = entity.questionId
    override val value = entity.value
    override val points = entity.points
    override val isMultipleChoice = entity.isMultipleChoice
}
