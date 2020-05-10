package us.testfor.domain.option

import org.springframework.data.jpa.repository.JpaRepository
import us.testfor.domain.option.model.OptionEntity

interface OptionRepository: JpaRepository<OptionEntity, Int>