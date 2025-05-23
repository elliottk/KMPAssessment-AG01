package org.example.project.util

import kotlinx.datetime.*

object DateUtils {
    
    /**
     * Converts Unix timestamp to "MMM dd, yyyy" format
     * @param timestampSeconds Unix timestamp in seconds
     * @return Formatted date string like "Jan 15, 2024"
     */
    fun formatToMMMddyyyy(timestampSeconds: Long): String {
        val instant = Instant.fromEpochSeconds(timestampSeconds)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        
        return formatLocalDateTime(localDateTime)
    }
    
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
    
    /**
     * Gets relative time string (e.g., "2 hours ago", "3 days ago")
     */
    fun getRelativeTimeString(timestampSeconds: Long): String {
        val now = Clock.System.now()
        val targetTime = Instant.fromEpochSeconds(timestampSeconds)
        val duration = now - targetTime
        
        return when {
            duration.inWholeMinutes < 1 -> "Just now"
            duration.inWholeMinutes < 60 -> "${duration.inWholeMinutes}m ago"
            duration.inWholeHours < 24 -> "${duration.inWholeHours}h ago"
            duration.inWholeDays < 7 -> "${duration.inWholeDays}d ago"
            duration.inWholeDays < 30 -> "${duration.inWholeDays / 7}w ago"
            else -> formatToMMMddyyyy(timestampSeconds)
        }
    }
    
    /**
     * Check if timestamp is today
     */
    fun isToday(timestampSeconds: Long): Boolean {
        val instant = Instant.fromEpochSeconds(timestampSeconds)
        val targetDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        
        return targetDate == today
    }

    /**
     * Check if timestamp is this week
     */
    fun isThisWeek(timestampSeconds: Long): Boolean {
        val now = Clock.System.now()
        val targetTime = Instant.fromEpochSeconds(timestampSeconds)
        val duration = now - targetTime
        
        return duration.inWholeDays < 7
    }
}

fun Long.toMMMddyyyy(): String = DateUtils.formatToMMMddyyyy(this)
fun Long.toRelativeTime(): String = DateUtils.getRelativeTimeString(this)
fun Long.isToday(): Boolean = DateUtils.isToday(this)
fun Long.isThisWeek(): Boolean = DateUtils.isThisWeek(this)