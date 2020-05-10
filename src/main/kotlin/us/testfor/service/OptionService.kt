package us.testfor.service

import org.springframework.orm.ObjectRetrievalFailureException
import org.springframework.stereotype.Service
import us.testfor.domain.option.OptionRepository
import us.testfor.domain.option.model.ExistedOption
import us.testfor.domain.option.model.NewOption
import us.testfor.domain.option.model.OptionEntity
import us.testfor.service.exception.OptionNotFoundException
import us.testfor.service.exception.QuestionNotFoundException
import javax.transaction.Transactional

@Service
class OptionService(
        private val repository: OptionRepository,
        private val questionnaireService: QuestionnaireService,
        private val questionService: QuestionService
) {
    @Transactional
    fun save(option: NewOption): ExistedOption {
        questionnaireService.getNotCommittedById(option.questionnaireId)
        val question = questionService.getExistedEntityById(option.questionId)
        if (question.questionnaire.questionnaireId != option.questionnaireId) {
            throw QuestionNotFoundException(option.questionId)
        }
        val newEntity = OptionEntity(
                optionId = 0,
                value = option.value,
                points = option.points,
                question = question,
                isRight = option.isRight,
                isCustom = option.isCustom
        )
        repository.save(newEntity);
        return ExistedOption(newEntity)
    }

    fun getById(id: Int): ExistedOption {
        return ExistedOption(getExistedEntityById(id))
    }

    internal fun getExistedEntityById(id: Int): OptionEntity {
        try {
            return repository.getOne(id)
        } catch (e: ObjectRetrievalFailureException) {
            throw OptionNotFoundException(id)
        }
    }

}
