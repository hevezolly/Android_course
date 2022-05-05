package hevezolly.habitstracker.domain.Model

data class HabitComplete(
    val type: HabitCompleteType,
    val rest: Int = 0
)

enum class HabitCompleteType() {
    BAD_MORE(),
    BAD_LESS(),
    GOOD_MORE(),
    GOOD_LESS(),
    NONE()
}