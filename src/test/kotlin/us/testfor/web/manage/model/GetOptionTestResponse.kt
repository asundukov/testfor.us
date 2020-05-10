package us.testfor.web.manage.model

data class GetOptionTestResponse (
        val id: Int,
        val value: String,
        val points: Int,
        val isRight: Boolean,
        val isCustom: Boolean
)
