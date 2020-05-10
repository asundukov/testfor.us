package us.testfor.service

import org.springframework.stereotype.Service
import us.testfor.domain.attempt.AttemptRepository
import us.testfor.domain.attempt.model.AttemptEntity
import us.testfor.domain.attempt.model.ExistedAttempt
import us.testfor.domain.questionnaire.QuestionnaireRepository
import javax.transaction.Transactional

@Service
class AttemptService(
        private val repository: AttemptRepository,
        private val questionnaireRepository: QuestionnaireRepository
) {
    private final val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    @Transactional
    fun create(questionnaireId: Int): ExistedAttempt {
        return ExistedAttempt(repository.save(AttemptEntity(
                questionnaire = questionnaireRepository.getOne(questionnaireId),
                attemptId = 0,
                isCompleted = false,
                token = (1..16)
                        .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                        .map(charPool::get)
                        .joinToString("")
        )))
    }

    @Transactional
    fun getById(id: Int): ExistedAttempt {
        return ExistedAttempt(repository.getOne(id))
    }

}
