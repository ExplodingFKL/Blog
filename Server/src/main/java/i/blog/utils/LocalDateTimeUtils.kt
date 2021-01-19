package i.design.utils

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LocalDateTimeUtils {
    private const val pattern = "yyy/MM/dd HH:mm:ss"
    private val dateFormat = SimpleDateFormat(pattern)
    private val format: DateTimeFormatter =
        DateTimeFormatter.ofPattern(pattern)
    val MIN = LocalDateTime.of(
        1970, 1, 1, 0, 0, 0
    )

    val MAX = LocalDateTime.of(
        3099, 12, 31, 0, 0, 0
    )

    fun format(localDateTime: LocalDateTime): String {
        return format.format(localDateTime)
    }
}