package org.example.project.util

import kotlinx.datetime.*

object DateUtils {
    
    /**
     * Converts Unix timestamp in milliseconds to "MMM dd, yyyy" format
     * @param timestampMillis Unix timestamp in milliseconds
     * @return Formatted date string like "Jan 15, 2024"
     */
    fun formatToMMMddyyyyFromMillis(timestampMillis: Long): String {
        val instant = Instant.fromEpochMilliseconds(timestampMillis)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        
        return formatLocalDateTime(localDateTime)
    }
    
    /**
     * Formats LocalDateTime to "MMM dd, yyyy" format
     */
    private fun formatLocalDateTime(dateTime: LocalDateTime): String {
        val month = getMonthAbbreviation(dateTime.month)
        val day = dateTime.dayOfMonth.toString().padStart(2, '0')
        val year = dateTime.year
        
        return "$month $day, $year"
    }
    
    /**
     * Gets month abbreviation in English
     */
    private fun getMonthAbbreviation(month: Month): String {
        return when (month) {
            Month.JANUARY -> "Jan"
            Month.FEBRUARY -> "Feb"
            Month.MARCH -> "Mar"
            Month.APRIL -> "Apr"
            Month.MAY -> "May"
            Month.JUNE -> "Jun"
            Month.JULY -> "Jul"
            Month.AUGUST -> "Aug"
            Month.SEPTEMBER -> "Sep"
            Month.OCTOBER -> "Oct"
            Month.NOVEMBER -> "Nov"
            else -> "Dec"
        }
    }
}