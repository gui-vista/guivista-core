package org.guiVista.core

import glib2.TRUE
import glib2.g_uuid_string_is_valid
import glib2.g_uuid_string_random
import kotlinx.cinterop.toKString

public actual fun uuidStringIsValid(str: String): Boolean = g_uuid_string_is_valid(str) == TRUE

public actual fun randomUuidString(): String = g_uuid_string_random()?.toKString() ?: ""
