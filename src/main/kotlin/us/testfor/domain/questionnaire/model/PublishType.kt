package us.testfor.domain.questionnaire.model

enum class PublishType(val id: Byte) {
    PRIVATE(1),
    DIRECT_LINK(2),
    PUBLIC(3);

    companion object {
        private val typeToId = mapOf(
                PRIVATE to PRIVATE.id,
                DIRECT_LINK to DIRECT_LINK.id,
                PUBLIC to PUBLIC.id
        )
        private val idToType = typeToId.entries.associate{(k,v)-> v to k}

        fun typeById(id: Byte): PublishType {
            return idToType[id]!!
        }

        fun idByType(type: PublishType): Byte {
            return typeToId[type]!!
        }
    }

}