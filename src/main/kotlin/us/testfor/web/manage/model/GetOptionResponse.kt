package us.testfor.web.manage.model

import us.testfor.domain.option.model.ExistedOption

class GetOptionResponse(option: ExistedOption) {
    val id = option.id
    val value = option.value
    val points = option.points
    val isRight = option.isRight
    val isCustom = option.isCustom
}
