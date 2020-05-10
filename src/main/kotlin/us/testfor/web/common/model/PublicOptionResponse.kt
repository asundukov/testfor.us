package us.testfor.web.common.model

import us.testfor.domain.option.model.ExistedOption

class PublicOptionResponse(option: ExistedOption) {
    val id = option.id
    val value = option.value
    val isCustom = option.isCustom
}
