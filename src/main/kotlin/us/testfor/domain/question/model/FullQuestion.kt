package us.testfor.domain.question.model

import us.testfor.domain.option.model.ExistedOption
import us.testfor.domain.questionnaire.model.QuestionEntity

class FullQuestion(entity: QuestionEntity): ExistedQuestion(entity) {
    val options = entity.options.map { item -> ExistedOption(item) }
}
