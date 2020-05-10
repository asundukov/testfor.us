package us.testfor.domain.question

import org.springframework.data.jpa.repository.JpaRepository
import us.testfor.domain.questionnaire.model.QuestionEntity

interface QuestionRepository: JpaRepository<QuestionEntity, Int>