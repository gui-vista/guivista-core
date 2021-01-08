package org.guiVista.core.dateTime

import glib2.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import org.guiVista.core.Closable

/** Represents a timezone. */
public actual class TimeZone private constructor(ptr: CPointer<GTimeZone>?) : Closable {
    public val gTimeZonePtr: CPointer<GTimeZone>? = ptr

    public actual val identifier: String = g_time_zone_get_identifier(gTimeZonePtr)?.toKString() ?: ""

    public actual companion object {
        public actual fun local(): TimeZone = TimeZone(g_time_zone_new_local())

        public actual fun utc(): TimeZone = TimeZone(g_time_zone_new_utc())

        public actual fun fromOffset(seconds: Int): TimeZone = TimeZone(g_time_zone_new_offset(seconds))

        public fun fromPointer(ptr: CPointer<GTimeZone>?): TimeZone = TimeZone(ptr)

        public actual fun fromIdentifier(identifier: String): TimeZone = TimeZone(g_time_zone_new(identifier))
    }

    public actual fun fetchAbbreviation(interval: Int): String =
        g_time_zone_get_abbreviation(gTimeZonePtr, interval)?.toKString() ?: ""

    public actual fun fetchOffset(interval: Int): Int = g_time_zone_get_offset(gTimeZonePtr, interval)

    public actual fun isDaylightSaving(interval: Int): Boolean = g_time_zone_is_dst(gTimeZonePtr, interval) == TRUE

    override fun close() {
        g_time_zone_unref(gTimeZonePtr)
    }
}
