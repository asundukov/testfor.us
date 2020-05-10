package us.testfor.web.common.model

import us.testfor.domain.answer.model.ExistedAnswer

class GetAnswerResponse(answer: ExistedAnswer) {
    val id = answer.id
    val questionId = answer.questionId
    val selectedOptions = answer.selectedOptions
}
