package us.testfor.domain.answer.model

import us.testfor.domain.option.model.OptionEntity
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "answer_option")
data class AnswerOptionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val answerOptionId: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    val answer: AnswerEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    val option: OptionEntity,

    val value: String,

    val points: Int
)
