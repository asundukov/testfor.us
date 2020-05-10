package us.testfor.domain.questionnaire

import org.springframework.data.jpa.repository.JpaRepository
import us.testfor.domain.questionnaire.model.QuestionnaireEntity

interface QuestionnaireRepository: JpaRepository<QuestionnaireEntity, Int>