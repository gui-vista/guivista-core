package org.guiVista.core.dateTime

import org.guiVista.core.Closable

/** Covers dates and times. */
public expect class DateTime : Closable {
    /** The year represented by [DateTime] in the Gregorian calendar. */
    public val year: Int

    /** The month of the year represented by [DateTime] in the Gregorian calendar. */
    public val month: Int

    /** The day of the month represented by [DateTime] in the gregorian calendar. */
    public val dayOfMonth: Int

    /** The ISO 8601 week-numbering year in which the week containing [DateTime] falls. */
    public val weekNumberingYear: Int

    /** The ISO 8601 week number for the week containing [DateTime]. */
    public val weekOfYear: Int

    /** The ISO 8601 day of the week on which [DateTime] falls (1 is Monday, 2 is Tuesday... 7 is Sunday). */
    public val dayOfWeek: Int

    /** The day of the year represented by [DateTime] in the Gregorian calendar. */
    public val dayOfYear: Int

    /** The hour of the day represented by [DateTime]. */
    public val hour: Int

    /** The minute of the hour represented by [DateTime]. */
    public val minute: Int

    /** The second of the minute represented by [DateTime]. */
    public val second: Int

    /** The microsecond of the date represented by [DateTime]. */
    public val microSecond: Int

    /**
     * The Unix time corresponding to [DateTime], rounding down to the nearest second. Unix time is the number of
     * seconds that have elapsed since 1970-01-01 00:00:00 UTC, regardless of the time zone associated with [DateTime].
     */
    public val unixTime: Long

    /** The number of microseconds that should be added to UTC to get the local time. */
    public val utcOffset: Long

    /** The time zone. */
    public val timeZone: TimeZone

    /** The time zone abbreviation. */
    public val timeZoneAbbrev: String

    /** If daylight savings time is in effect at the time, and in the time zone of [DateTime]. */
    public val isDaylightSavings: Boolean

    /**
     * Creates a copy of [DateTime], and adds the specified [timeSpan] to the copy.
     * @param timeSpan The time span.
     * @return The newly created [DateTime] which should be freed with [close], or *null*.
     */
    public infix fun addTimeSpan(timeSpan: Long): DateTime?

    /**
     * Creates a copy of [DateTime], and adds the specified number of years to the copy. Add negative values to
     * subtract years. As with [addMonths] if the resulting date would be 29th February on a non-leap year, the day
     * will be clamped to 28th February.
     * @param years The number of years.
     * @return The newly created [DateTime] which should be freed with [close], or *null*.
     */
    public infix fun addYears(years: Int): DateTime?

    /**
     * Creates a copy of [DateTime] and adds the specified number of months to the copy. Add negative values to
     * subtract months. The day of the month of the resulting [DateTime] is clamped to the number of days in the
     * updated calendar month. For example if adding 1 month to 31st January 2018, the result would be 28th February
     * 2018. In 2020 (a leap year), the result would be 29th February.
     * @param months The number of months.
     * @return The newly created [DateTime] which should be freed with [close], or *null*.
     */
    public infix fun addMonths(months: Int): DateTime?

    /**
     * Creates a copy of [DateTime] and adds the specified number of weeks to the copy. Add negative values to
     * subtract weeks.
     * @param weeks The number of weeks.
     * @return The newly created [DateTime] which should be freed with [close], or *null*.
     */
    public infix fun addWeeks(weeks: Int): DateTime?

    /**
     * Creates a copy of [DateTime], and adds the specified number of days to the copy. Add negative values to
     * subtract days.
     * @param days The number of days.
     * @return The newly created [DateTime] which should be freed with [close], or *null*.
     */
    public infix fun addDays(days: Int): DateTime?

    /**
     * Creates a copy of [DateTime] and adds the specified number of hours. Add negative values to subtract hours.
     * @param hours The number of hours.
     * @return The newly created [DateTime] which should be freed with [close], or *null*.
     */
    public infix fun addHours(hours: Int): DateTime?

    /**
     * Creates a copy of [DateTime] adding the specified number of minutes. Add negative values to subtract minutes.
     * @param minutes The number of minutes.
     * @return The newly created [DateTime] which should be freed with [close], or *null*.
     */
    public infix fun addMinutes(minutes: Int): DateTime?

    /**
     * Creates a copy of datetime and adds the specified number of seconds. Add negative values to subtract seconds.
     * @param seconds The number of seconds.
     * @return The newly created [DateTime] which should be freed with [close], or *null*.
     */
    public infix fun addSeconds(seconds: Double): DateTime?

    public companion object {
        /**
         * Calculates the difference in time between [end], and [begin]. The result that is returned is effectively
         * end - begin (ie: positive if the first parameter is larger).
         * @param begin The begging.
         * @param end The ending.
         * @return The difference between [end] and [begin] as a time span expressed in microseconds.
         */
        public fun difference(begin: DateTime, end: DateTime): Long

        /**
         * A comparison function for date times.
         * @param dateTime1 First [DateTime] to compare.
         * @param dateTime2 Second [DateTime] to compare.
         * @return A value of *-1*, *0* or *1* if [dateTime1] is less than, equal to, or greater than [dateTime2].
         */
        public fun compare(dateTime1: DateTime, dateTime2: DateTime): Int

        /**
         * Creates a [DateTime] corresponding to this exact instant in the given [time zone][timeZone]. The time is as
         * accurate as the system allows, to a maximum accuracy of 1 microsecond. This function will always succeed
         * unless GLib is still being used after the year 9999.
         *
         * You should release the return value by calling [close] when you are done with it.
         * @param timeZone A time zone.
         * @return A new [DateTime] or *null*.
         */
        public fun now(timeZone: TimeZone): DateTime?

        /**
         * Creates a [DateTime] corresponding to this exact instant in the local time zone.
         * @return A new [DateTime] or *null*.
         */
        public fun nowWithLocalTimeZone(): DateTime?

        /**
         * Creates a GDateTime corresponding to this exact instant in UTC.
         * @return A new [DateTime] or *null*.
         */
        public fun nowWithUtcTimeZone(): DateTime?

        /**
         * Creates a [DateTime] corresponding to the given [Unix time][unixTime] in the local time zone. Unix time is
         * the number of seconds that have elapsed since 1970-01-01 00:00:00 UTC, regardless of the local time offset.
         * This call can fail (returning *null*) if [unixTime] represents a time outside of the supported range of
         * [DateTime].
         *
         * You should release the return value by calling [close] when you are done with it.
         * @param unixTime The Unix time.
         * @return A new [DateTime] or *null*.
         */
        public fun fromUnixLocal(unixTime: Long): DateTime?
    }

    /**
     * Creates a new [DateTime] adding the specified values to the current date and time in [DateTime]. Add negative
     * values to subtract.
     * @param years The number of years to add.
     * @param months The number of months to add.
     * @param days The number of days to add.
     * @param hours The number of hours to add.
     * @param minutes The number of minutes to add.
     * @param seconds The number of seconds to add.
     * @return The newly created [DateTime] which should be freed with [close], or *null*.
     */
    public fun addDateAndTime(years: Int, months: Int, days: Int, hours: Int, minutes: Int, seconds: Double): DateTime?

    /**
     * Create a new [DateTime] corresponding to the same instant in time as [DateTime], but in the
     * [time zone][timeZone]. This call can fail in the case that the time goes out of bounds. For example converting
     * 0001-01-01 00:00:00 UTC to a time zone west of Greenwich will fail (due to the year 0 being out of range).
     * @param timeZone The new time zone.
     * @return The newly created [DateTime] which should be freed with [close], or *null*.
     */
    public fun toTimeZone(timeZone: TimeZone): DateTime?

    /**
     * Creates a new [DateTime] corresponding to the same instant in time as [DateTime], but in the local time zone.
     * @return The newly created [DateTime] which should be freed with [close], or *null*.
     */
    public fun toLocalTimeZone(): DateTime?

    /**
     * Creates a new [DateTime] corresponding to the same instant in time as [DateTime], but in UTC.
     * @return The newly created [DateTime] which should be freed with [close], or *null*.
     */
    public fun toUtc(): DateTime?

    /**
     * Creates a newly allocated string representing the requested format. The format strings understood by this
     * function are a subset of the `strftime()` format language as specified by C99. The %D, %U and %W conversions are
     * not supported, nor is the **E** modifier. The GNU extensions %k, %l, %s and %P are supported, however, as are
     * the **0**, **_**, and **-** modifiers. The Python extension %f is also supported.
     *
     * In contrast to `strftime()` this function always produces a UTF-8 string, regardless of the current locale. Note
     * that the rendering of many formats is locale-dependent, and may not match the `strftime()` output exactly. The
     * following format specifiers are supported:
     *
     * - %a: the abbreviated weekday name according to the current locale
     * - %A: the full weekday name according to the current locale
     * - %b: the abbreviated month name according to the current locale
     * - %B: the full month name according to the current locale
     * - %c: the preferred date and time representation for the current locale
     * - %C: the century number (year/100) as a 2-digit integer (00-99)
     * - %d: the day of the month as a decimal number (range 01 to 31)
     * - %e: the day of the month as a decimal number (range 1 to 31)
     * - %F: equivalent to %Y-%m-%d (the ISO 8601 date format)
     * - %g: the last two digits of the ISO 8601 week-based year as a decimal number (00-99). This works well with %V and %u.
     * - %G: the ISO 8601 week-based year as a decimal number. This works well with %V and %u.
     * - %h: equivalent to %b
     * - %H: the hour as a decimal number using a 24-hour clock (range 00 to 23)
     * - %I: the hour as a decimal number using a 12-hour clock (range 01 to 12)
     * - %j: the day of the year as a decimal number (range 001 to 366)
     * - %k: the hour (24-hour clock) as a decimal number (range 0 to 23); single digits are preceded by a blank
     * - %l: the hour (12-hour clock) as a decimal number (range 1 to 12); single digits are preceded by a blank
     * - %m: the month as a decimal number (range 01 to 12)
     * - %M: the minute as a decimal number (range 00 to 59)
     * - %f: the microsecond as a decimal number (range 000000 to 999999)
     * - %p: either "AM" or "PM" according to the given time value, or the corresponding strings for the current locale. Noon is treated as "PM" and midnight as "AM". Use of this format specifier is discouraged, as many locales have no concept of AM/PM formatting. Use %c or %X instead.
     * - %P: like %p but lowercase: "am" or "pm" or a corresponding string for the current locale. Use of this format specifier is discouraged, as many locales have no concept of AM/PM formatting. Use %c or %X instead.
     * - %r: the time in a.m. or p.m. notation. Use of this format specifier is discouraged, as many locales have no concept of AM/PM formatting. Use %c or %X instead.
     * - %R: the time in 24-hour notation (%H:%M)
     * - %s: the number of seconds since the Epoch, that is, since 1970-01-01 00:00:00 UTC
     * - %S: the second as a decimal number (range 00 to 60)
     * - %t: a tab character
     * - %T: the time in 24-hour notation with seconds (%H:%M:%S)
     * - %u: the ISO 8601 standard day of the week as a decimal, range 1 to 7, Monday being 1. This works well with %G and %V.
     * - %V: the ISO 8601 standard week number of the current year as a decimal number, range 01 to 53, where week 1 is the first week that has at least 4 days in the new year. See g_date_time_get_week_of_year(). This works well with %G and %u.
     * - %w: the day of the week as a decimal, range 0 to 6, Sunday being 0. This is not the ISO 8601 standard format -- use %u instead.
     * - %x: the preferred date representation for the current locale without the time
     * - %X: the preferred time representation for the current locale without the date
     * - %y: the year as a decimal number without the century
     * - %Y: the year as a decimal number including the century
     * - %z: the time zone as an offset from UTC (+hhmm)
     * - %:z: the time zone as an offset from UTC (+hh:mm). This is a gnulib strftime() extension. Since: 2.38
     * - %::z: the time zone as an offset from UTC (+hh:mm:ss). This is a gnulib strftime() extension. Since: 2.38
     * - %:::z: the time zone as an offset from UTC, with : to necessary precision (e.g., -04, +05:30). This is a gnulib strftime() extension. Since: 2.38
     * - %Z: the time zone or name or abbreviation
     * - %%: a literal % character
     *
     * Some conversion specifications can be modified by preceding the conversion specifier by one or more modifier
     * characters. The following modifiers are supported for many of the numeric conversions:
     *
     * - O: Use alternative numeric symbols, if the current locale supports those.
     * - _: Pad a numeric result with spaces. This overrides the default padding for the specifier.
     * - -: Do not pad a numeric result. This overrides the default padding for the specifier.
     * - 0: Pad a numeric result with zeros. This overrides the default padding for the specifier.
     */
    public fun formatAsString(format: String): String
}
