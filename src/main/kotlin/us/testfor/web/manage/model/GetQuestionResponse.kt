package us.testfor.web.manage.model

import us.testfor.domain.question.model.ExistedQuestion

class GetQuestionResponse(q: ExistedQuestion) {
    val id = q.id
    val value = q.value
    val points = q.points
    val isMultipleChoice = q.isMultipleChoice
}
