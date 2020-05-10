package us.testfor.service

import org.springframework.stereotype.Service
import us.testfor.domain.answer.AnswerRepository
import us.testfor.domain.answer.model.AnswerEntity
import us.testfor.domain.answer.model.AnswerOption
import us.testfor.domain.answer.model.AnswerOptionEntity
import us.testfor.domain.answer.model.ExistedAnswer
import us.testfor.domain.answer.model.NewAnswer
import us.testfor.domain.attempt.AttemptRepository
import us.testfor.domain.attempt.model.AttemptEntity
import us.testfor.domain.option.OptionRepository
import us.testfor.domain.question.QuestionRepository
import us.testfor.domain.questionnaire.model.QuestionEntity
import javax.transaction.Transactional

@Service
class AnswerService(
        private val repository: AnswerRepository,
        private val attemptRepository: AttemptRepository,
        private val questionRepository: QuestionRepository,
        private val optionRepository: OptionRepository
) {
    @Transactional
    fun answer(newAnswer: NewAnswer): ExistedAnswer {
        val attempt = attemptRepository.getOne(newAnswer.attemptId);
        val question = questionRepository.getOne(newAnswer.questionId);
        val entity = getOrCreate(attempt, question);
        entity.answerOptions.clear()
        entity.answerOptions.addAll(saveAnswerOptions(newAnswer.selectedOptions, entity))
        repository.save(entity)
        return ExistedAnswer(entity)
    }


    @Transactional
    fun getByQuestionAndAttemptId(attemptId: Int): List<ExistedAnswer> {
        return repository.findAllByAttempt_AttemptId(attemptId).map { item -> ExistedAnswer(item) }
    }

    private fun getOrCreate(attempt: AttemptEntity, question: QuestionEntity): AnswerEntity {
        var entity = repository.findByAttemptAndQuestion(attempt, question)
        if (entity == null) {
            entity = AnswerEntity(
                    answerId = 0,
                    attempt = attempt,
                    question = question,
                    points = 0,
                    answerOptions = ArrayList()
            )
            repository.save(entity)
        }
        return entity
    }

    private fun saveAnswerOptions(selectedOptions: List<AnswerOption>, answer: AnswerEntity)
            : MutableList<AnswerOptionEntity> {
        val answerOptionEntities = ArrayList<AnswerOptionEntity>()
        for (answerOption in selectedOptions) {
            val entity = AnswerOptionEntity(
                    answerOptionId = 0,
                    points = 0,
                    value = answerOption.value,
                    option = optionRepository.getOne(answerOption.optionId),
                    answer = answer
            )
            //answerOptionRepository.save(entity)
            answerOptionEntities.add(entity)
        }
        return answerOptionEntities
    }
}