package us.testfor.domain.questionnaire.model

import us.testfor.domain.attempt.model.AttemptEntity
import java.util.Calendar
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity(name = "questionnaire")
class QuestionnaireEntity(
        @Id
        @GeneratedValue(strategy = IDENTITY)
        val questionnaireId: Int,

        var title: String,

        var description: String,

        var openStat: Boolean,

        var openResult: Boolean,

        var publishTypeId: Byte,

        val createdAt: Calendar,

        var committedAt: Calendar?,

        @OneToMany(mappedBy = "questionnaire")
        val questions: List<QuestionEntity>,

        @OneToMany(mappedBy = "questionnaire")
        val attempts: List<AttemptEntity>
) {
    fun getPublishType(): PublishType {
         return PublishType.typeById(publishTypeId)
    }

    fun setPublishType(type: PublishType) {
         publishTypeId = PublishType.idByType(type)
    }
}
