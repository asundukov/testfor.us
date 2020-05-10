package us.testfor.domain.option.model

import us.testfor.domain.questionnaire.model.QuestionEntity
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "option")
data class OptionEntity(
        @Id
        @GeneratedValue(strategy = IDENTITY)
        val optionId: Int,

        val value: String,

        val isRight: Boolean,

        val points: Int,

        @ManyToOne(fetch = LAZY)
        @JoinColumn(name = "question_id")
        val question: QuestionEntity,

        val isCustom: Boolean
)
