package us.testfor.service.exception

import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(CONFLICT)
class QuestionnaireNotEnoughQuestionsCommitConflict(val id: Int)
    : RuntimeException("Questionnaire {$id} doesn't have questions to commit")
