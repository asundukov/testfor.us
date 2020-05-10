package us.testfor.domain.questionnaire.model

import us.testfor.domain.option.model.OptionEntity
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity(name = "question")
data class QuestionEntity(
        @Id
        @GeneratedValue(strategy = IDENTITY)
        val questionId: Int,

        val value: String,

        val points: Int,

        val isMultipleChoice: Boolean,

        @ManyToOne(fetch = LAZY)
        @JoinColumn(name = "questionnaire_id")
        val questionnaire: QuestionnaireEntity,

        @OneToMany(mappedBy = "question")
        val options: List<OptionEntity>

)
