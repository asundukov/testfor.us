package us.testfor.domain.option.model

data class ExistedOption(val entity: OptionEntity): Option {
    val id: Int = entity.optionId
    override val value = entity.value
    override val points = entity.points
    override val isRight = entity.isRight
    override val isCustom = entity.isCustom
}
