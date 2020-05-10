package us.testfor.web.manage.model

import us.testfor.domain.questionnaire.model.PublishType
import java.util.Calendar

class GetQuestionnaireTestResponse(
        val id: Int,
        val title: String,
        val description: String,
        val createdAt: Calendar,
        val openStat: Boolean,
        val openResult: Boolean,
        val publishType: PublishType,
        val committedAt: Calendar?

)
