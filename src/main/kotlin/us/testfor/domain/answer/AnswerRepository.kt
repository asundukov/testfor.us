package us.testfor.domain.answer

import org.springframework.data.jpa.repository.JpaRepository
import us.testfor.domain.answer.model.AnswerEntity
import us.testfor.domain.attempt.model.AttemptEntity
import us.testfor.domain.questionnaire.model.QuestionEntity

interface AnswerRepository: JpaRepository<AnswerEntity, Int> {
    fun findAllByAttempt_AttemptId(attemptId: Int): List<AnswerEntity>
    fun findByAttemptAndQuestion(attempt: AttemptEntity, question: QuestionEntity): AnswerEntity?
}
