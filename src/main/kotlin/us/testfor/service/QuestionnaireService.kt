package us.testfor.service

import org.springframework.orm.ObjectRetrievalFailureException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import us.testfor.domain.questionnaire.QuestionnaireRepository
import us.testfor.domain.questionnaire.model.ExistedQuestionnaire
import us.testfor.domain.questionnaire.model.FullQuestionnaire
import us.testfor.domain.questionnaire.model.PublishType
import us.testfor.domain.questionnaire.model.PublishType.PUBLIC
import us.testfor.domain.questionnaire.model.Questionnaire
import us.testfor.domain.questionnaire.model.QuestionnaireEntity
import us.testfor.service.exception.QuestionnaireAlreadyCommitted
import us.testfor.service.exception.QuestionnaireForbiddenException
import us.testfor.service.exception.QuestionnaireNotEnoughOptionsCommitConflict
import us.testfor.service.exception.QuestionnaireNotEnoughQuestionsCommitConflict
import us.testfor.service.exception.QuestionnaireNotFoundException
import java.util.Calendar

@Service
class QuestionnaireService(
    private val repository: QuestionnaireRepository
) {
    @Transactional
    fun save(q: Questionnaire): ExistedQuestionnaire {
        val newEntity = QuestionnaireEntity(
                questionnaireId = 0,
                description = q.description,
                title = q.title,
                openResult = q.openResult,
                openStat = q.openStat,
                questions = emptyList(),
                attempts = emptyList(),
                publishTypeId = PublishType.idByType(q.publishType),
                createdAt = Calendar.getInstance(),
                committedAt = null
        )
        repository.save(newEntity);
        return ExistedQuestionnaire(newEntity)
    }

    @Transactional(readOnly = true)
    fun getById(id: Int): ExistedQuestionnaire {
        return ExistedQuestionnaire(getExistedById(id))
    }

    @Transactional(readOnly = true)
    fun getFullById(id: Int): FullQuestionnaire {
        val entity = getExistedById(id)
        if (entity.getPublishType() != PUBLIC || entity.committedAt == null) {
            throw QuestionnaireForbiddenException(id);
        }
        return FullQuestionnaire(entity)
    }

    @Transactional
    fun commit(id: Int) {
        val entity = getExistedById(id)
        if (entity.questions.isEmpty()) {
            throw QuestionnaireNotEnoughQuestionsCommitConflict(id)
        }
        for (question in entity.questions) {
            if (question.options.isEmpty()) {
                throw QuestionnaireNotEnoughOptionsCommitConflict(id, question.questionId)
            }
        }
        entity.committedAt = Calendar.getInstance()
        repository.save(entity)
    }

    internal fun getNotCommittedById(id: Int): QuestionnaireEntity {
        val entity = getExistedById(id)
        if (entity.committedAt != null) {
            throw QuestionnaireAlreadyCommitted(id)
        }
        return entity
    }

    internal fun getExistedById(id: Int): QuestionnaireEntity {
        try {
            return repository.getOne(id)
        } catch (e: ObjectRetrievalFailureException) {
            throw QuestionnaireNotFoundException(id, e)
        }
    }

}
