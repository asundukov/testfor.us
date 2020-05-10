package us.testfor.service

import org.springframework.orm.ObjectRetrievalFailureException
import org.springframework.stereotype.Service
import us.testfor.domain.question.QuestionRepository
import us.testfor.domain.questionnaire.QuestionnaireRepository
import us.testfor.domain.question.model.ExistedQuestion
import us.testfor.domain.questionnaire.model.NewQuestion
import us.testfor.domain.questionnaire.model.QuestionEntity
import us.testfor.service.exception.QuestionNotFoundException
import javax.transaction.Transactional

@Service
class QuestionService(
        private val repository: QuestionRepository,
        private val questionnaireService: QuestionnaireService
) {
    @Transactional
    fun save(q: NewQuestion): ExistedQuestion {
        val questionnaireEntity = questionnaireService.getNotCommittedById(q.questionnaireId)
        val newEntity = QuestionEntity(
                questionId = 0,
                value = q.value,
                points = q.points,
                questionnaire = questionnaireEntity,
                options = emptyList(),
                isMultipleChoice =  q.isMultipleChoice
        )
        val existedEntity = repository.save(newEntity);
        return ExistedQuestion(existedEntity)
    }

    @Transactional
    fun getById(id: Int): ExistedQuestion {
        return ExistedQuestion(getExistedEntityById(id))
    }

    internal fun getExistedEntityById(id: Int): QuestionEntity {
        try {
            return repository.getOne(id)
        } catch (e: ObjectRetrievalFailureException) {
            throw QuestionNotFoundException(id)
        }
    }

}
