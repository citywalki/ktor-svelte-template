package domain.user

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class UserStats(
    val name: String,
    val memoDisplayTimestamps: List<LocalDateTime>,
    val memoTypeStats: MemoTypeStats,
    val tagCount: Map<String, Int>,
) {
    @Serializable
    data class MemoTypeStats(
        val linkCount: Int,
        val codeCount: Int,
        val todoCount: Int,
        val undoCount: Int,
    )
}
