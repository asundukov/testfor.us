package us.testfor.domain.answer.model

import us.testfor.domain.attempt.model.AttemptEntity
import us.testfor.domain.questionnaire.model.QuestionEntity
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType.EAGER
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity(name = "answer")
data class AnswerEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val answerId: Int,

        @ManyToOne(fetch = LAZY)
        @JoinColumn(name = "attempt_id")
        val attempt: AttemptEntity,

        @ManyToOne
        @JoinColumn(name = "question_id")
        val question: QuestionEntity,

        val points: Int,

        @OneToMany(mappedBy = "answer", fetch = EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
        val answerOptions: MutableList<AnswerOptionEntity>
)

