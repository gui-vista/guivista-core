package org.guiVista.core.dateTime

import glib2.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import org.guiVista.core.Closable

public actual class DateTime private constructor(ptr: CPointer<GDateTime>?) : Closable {
    public val gDateTimePtr: CPointer<GDateTime>? = ptr

    public actual val year: Int
        get() = g_date_time_get_year(gDateTimePtr)
    public actual val month: Int
        get() = g_date_time_get_month(gDateTimePtr)
    public actual val dayOfMonth: Int
        get() = g_date_time_get_day_of_month(gDateTimePtr)
    public actual val weekNumberingYear: Int
        get() = g_date_time_get_week_numbering_year(gDateTimePtr)
    public actual val weekOfYear: Int
        get() = g_date_time_get_week_of_year(gDateTimePtr)
    public actual val dayOfWeek: Int
        get() = g_date_time_get_day_of_week(gDateTimePtr)
    public actual val dayOfYear: Int
        get() = g_date_time_get_day_of_year(gDateTimePtr)
    public actual val hour: Int
        get() = g_date_time_get_hour(gDateTimePtr)
    public actual val minute: Int
        get() = g_date_time_get_minute(gDateTimePtr)
    public actual val second: Int
        get() = g_date_time_get_second(gDateTimePtr)
    public actual val microSecond: Int
        get() = g_date_time_get_microsecond(gDateTimePtr)
    public actual val unixTime: Long
        get() = g_date_time_to_unix(gDateTimePtr)
    public actual val utcOffset: Long
        get() = g_date_time_get_utc_offset(gDateTimePtr)
    public actual val timeZone: TimeZone
        get() = TimeZone.fromPointer(g_date_time_get_timezone(gDateTimePtr))
    public actual val timeZoneAbbrev: String
        get() = g_date_time_get_timezone_abbreviation(gDateTimePtr)?.toKString() ?: ""
    public actual val isDaylightSavings: Boolean
        get() = g_date_time_is_daylight_savings(gDateTimePtr) == TRUE

    public actual infix fun addTimeSpan(timeSpan: Long): DateTime? {
        val ptr = g_date_time_add(gDateTimePtr, timeSpan)
        return if (ptr != null) DateTime(ptr) else null
    }

    public actual infix fun addYears(years: Int): DateTime? {
        val ptr = g_date_time_add_years(gDateTimePtr, years)
        return if (ptr != null) DateTime(ptr) else null
    }

    public actual infix fun addMonths(months: Int): DateTime? {
        val ptr = g_date_time_add_months(gDateTimePtr, months)
        return if (ptr != null) DateTime(ptr) else null
    }

    public actual infix fun addWeeks(weeks: Int): DateTime? {
        val ptr = g_date_time_add_weeks(gDateTimePtr, weeks)
        return if (ptr != null) DateTime(ptr) else null
    }

    public actual infix fun addDays(days: Int): DateTime? {
        val ptr = g_date_time_add_days(gDateTimePtr, days)
        return if (ptr != null) DateTime(ptr) else null
    }

    public actual infix fun addHours(hours: Int): DateTime? {
        val ptr = g_date_time_add_hours(gDateTimePtr, hours)
        return if (ptr != null) DateTime(ptr) else null
    }

    public actual infix fun addMinutes(minutes: Int): DateTime? {
        val ptr = g_date_time_add_minutes(gDateTimePtr, minutes)
        return if (ptr != null) DateTime(ptr) else null
    }

    public actual infix fun addSeconds(seconds: Double): DateTime? {
        val ptr = g_date_time_add_seconds(gDateTimePtr, seconds)
        return if (ptr != null) DateTime(ptr) else null
    }

    public actual companion object {
        public actual fun difference(begin: DateTime, end: DateTime): Long =
            g_date_time_difference(end = end.gDateTimePtr, begin = begin.gDateTimePtr)

        public actual fun compare(dateTime1: DateTime, dateTime2: DateTime): Int =
            g_date_time_compare(dateTime1.gDateTimePtr, dateTime2.gDateTimePtr)

        public actual fun now(timeZone: TimeZone): DateTime? {
            val ptr = g_date_time_new_now(timeZone.gTimeZonePtr)
            return if (ptr != null) DateTime(ptr) else null
        }

        public actual fun nowWithLocalTimeZone(): DateTime? {
            val ptr = g_date_time_new_now_local()
            return if (ptr != null) DateTime(ptr) else null
        }

        public actual fun nowWithUtcTimeZone(): DateTime? {
            val ptr = g_date_time_new_now_utc()
            return if (ptr != null) DateTime(ptr) else null
        }

        public actual fun fromUnixLocal(unixTime: Long): DateTime? {
            val ptr = g_date_time_new_from_unix_local(unixTime)
            return if (ptr != null) DateTime(ptr) else null
        }
    }

    public actual fun addDateAndTime(
        years: Int,
        months: Int,
        days: Int,
        hours: Int,
        minutes: Int,
        seconds: Double
    ): DateTime? {
        val ptr = g_date_time_add_full(
            datetime = gDateTimePtr,
            years = years,
            months = months,
            days = days,
            hours = hours,
            minutes = minutes,
            seconds = seconds
        )
        return if (ptr != null) DateTime(ptr) else null
    }

    public actual fun toTimeZone(timeZone: TimeZone): DateTime? {
        val ptr = g_date_time_to_timezone(gDateTimePtr, timeZone.gTimeZonePtr)
        return if (ptr != null) DateTime(ptr) else null
    }

    public actual fun toLocalTimeZone(): DateTime? {
        val ptr = g_date_time_to_local(gDateTimePtr)
        return if (ptr != null) DateTime(ptr) else null
    }

    public actual fun toUtc(): DateTime? {
        val ptr = g_date_time_to_utc(gDateTimePtr)
        return if (ptr != null) DateTime(ptr) else null
    }

    public actual fun formatAsString(format: String): String =
        g_date_time_format(gDateTimePtr, format)?.toKString() ?: ""

    override fun close() {
        g_date_time_unref(gDateTimePtr)
    }
}
