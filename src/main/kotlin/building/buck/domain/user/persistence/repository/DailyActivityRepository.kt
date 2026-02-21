package building.buck.domain.user.persistence.repository

import building.buck.domain.user.persistence.DailyActivity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DailyActivityRepository : CrudRepository<DailyActivity, String>
