package us.testfor.domain.attempt.model

import us.testfor.domain.questionnaire.model.QuestionnaireEntity
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "attempt")
data class AttemptEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val attemptId: Int,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "questionnaire_id")
        val questionnaire: QuestionnaireEntity,

        val token: String,

        val isCompleted: Boolean
)
