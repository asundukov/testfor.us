package us.testfor.domain.attempt

import org.springframework.data.jpa.repository.JpaRepository
import us.testfor.domain.attempt.model.AttemptEntity

interface AttemptRepository: JpaRepository<AttemptEntity, Int>