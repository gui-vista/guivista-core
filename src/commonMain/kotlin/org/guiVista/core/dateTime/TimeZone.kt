package org.guiVista.core.dateTime

import org.guiVista.core.Closable

/** Represents a timezone. */
public expect class TimeZone : Closable {
    /**
     * Get the identifier of this GTimeZone. If the identifier passed at construction time was not recognised, UTC will
     * be returned. If it was *null* then the identifier of the local timezone at construction time will be returned.
     * The identifier will be returned in the same format as provided at construction time: if provided as a time
     * offset that will be returned by this property.
     */
    public val identifier: String

    public companion object {
        /**
         * Creates a [TimeZone] corresponding to the local time. The local time zone *may* change between invocations
         * to this function; for example, if the system administrator changes it. You should release the return value
         * by calling [close] when you are done with it.
         * @return The local timezone.
         */
        public fun local(): TimeZone

        /**
         * Creates a [TimeZone] corresponding to UTC. You should release the return value by calling [close] when you
         * are done with it.
         * @return The universal timezone.
         */
        public fun utc(): TimeZone

        /**
         * Creates a [TimeZone] corresponding to the given constant offset from UTC in seconds.
         * @param seconds Offset to UTC in seconds.
         * @return A timezone at the given offset from UTC.
         */
        public fun fromOffset(seconds: Int): TimeZone

        /**
         * Creates a new [TimeZone] corresponding to identifier. The [identifier] can either be an RFC3339/ISO 8601
         * time offset, or something that would pass as a valid value for the TZ environment variable (including *""*).
         * In Windows the [identifier] can also be the unlocalized name of a time zone for standard time, for example
         * **Pacific Standard Time**. Valid RFC3339 time offsets are **Z** (for UTC) or **±hh:mm**. ISO 8601
         * additionally specifies **±hhmm** and **±hh**. Offsets are time values to be added to Coordinated Universal
         * Time (UTC) to get the local time.
         *
         * In UNIX the TZ environment variable typically corresponds to the name of a file in the *zoneinfo* database,
         * an absolute path to a file somewhere else, or a string in
         * "std offset [dst [offset],start[/time],end[/time]]" (POSIX) format. There are no spaces in the
         * specification. The name of standard and daylight savings time zone must be three or more alphabetic
         * characters. Offsets are time values to be added to local time to get Coordinated Universal Time (UTC), and
         * should be **[±]hh[[:]mm[:ss]]**. Dates are either **Jn** (Julian day with n between 1 and 365, leap years
         * not counted), **n** (zero-based Julian day with n between 0 and 365) or **Mm.w.d** (day d (0 <= d <= 6) of
         * week w (1 <= w <= 5) of month m (1 <= m <= 12), day 0 is a Sunday). Times are in local wall clock time, the
         * default is 02:00:00.
         *
         * In Windows the **tzn[+|–]hh[:mm[:ss]][dzn]** format is used, but also accepts POSIX format. The Windows
         * format uses US rules for all time zones; daylight savings time is 60 minutes behind the standard time with
         * date and time of change taken from Pacific Standard Time. Offsets are time values to be added to the local
         * time to get Coordinated Universal Time (UTC).
         *
         * Note that `g_time_zone_new_local()` calls this function with the value of the TZ environment variable. This
         * function itself is independent of the value of TZ, but if identifier is *null* then /etc/localtime **WILL**
         * be consulted to discover the correct time zone on UNIX, and the registry will be consulted or
         * `GetTimeZoneInformation()` will be used to get the local time zone on Windows. If intervals are not
         * available, only time zone rules from TZ environment variable or other means, then they will be computed from
         * year 1900 to 2037. If the maximum year for the rules is available and it is greater than *2037*, then it
         * will followed instead.
         *
         * See RFC3339 §5.6 for a precise definition of valid RFC3339 time offsets (the time-offset expansion), and ISO
         * 8601 for the full list of valid time offsets. See the GNU C Library manual for an explanation of the
         * possible values of the TZ environment variable. See Microsoft Time Zone Index Values for the list of time
         * zones on Windows.
         *
         * You should release the return value by calling [close] when you are done with it.
         */
        public fun fromIdentifier(identifier: String): TimeZone
    }

    /**
     * Determines the time zone abbreviation to be used during a particular interval of time in the time zone tz. For
     * example in Toronto this is currently "EST" during the winter months, and "EDT" during the summer months when
     * daylight savings time is in effect.
     * @param interval An interval with the timezone.
     * @return The time zone abbreviation which belongs to [TimeZone].
     */
    public fun fetchAbbreviation(interval: Int): String

    /**
     * Determines the offset to UTC in effect during a particular interval of time in the time zone tz. The offset is
     * the number of seconds that you add to UTC time to arrive at local time for tz (ie: negative numbers for time
     * zones west of GMT, positive numbers for east).
     * @param interval An interval within the timezone.
     * @return The number of seconds that should be added to UTC to get the local time in [TimeZone].
     */
    public fun fetchOffset(interval: Int): Int

    /**
     * Determines if daylight savings time is in effect during a particular interval of time in the time zone
     * [TimeZone].
     * @param interval An interval within the timezone.
     * @return A value of *true* if daylight savings time is in effect.
     */
    public fun isDaylightSaving(interval: Int): Boolean
}
