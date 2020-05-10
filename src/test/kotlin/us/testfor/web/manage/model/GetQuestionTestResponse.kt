package us.testfor.web.manage.model

data class GetQuestionTestResponse (
        val id: Int,
        val value: String,
        val points: Int,
        val isMultipleChoice: Boolean
)
