package us.testfor.web.common.model

import us.testfor.domain.question.model.FullQuestion

class PublicQuestionResponse(q: FullQuestion) {
    val id = q.id
    val value = q.value
    val isMultipleChoice = q.isMultipleChoice
    val options = q.options.map {item -> PublicOptionResponse(item)}
}
