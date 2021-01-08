package org.guiVista.core.dataType

import glib2.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString

/** Strongly typed value data type. */
public actual class Variant private constructor(ptr: CPointer<GVariant>?) {
    public val gVariantPtr: CPointer<GVariant>? = ptr

    public actual val totalChildren: ULong
        get() = g_variant_n_children(gVariantPtr)

    public actual val booleanValue: Boolean = g_variant_get_boolean(gVariantPtr) == TRUE

    public actual val uByteValue: UByte = g_variant_get_byte(gVariantPtr)

    public actual val shortValue: Short = g_variant_get_int16(gVariantPtr)

    public actual val uShortValue: UShort = g_variant_get_uint16(gVariantPtr)

    public actual val intValue: Int = g_variant_get_int32(gVariantPtr)

    public actual val uIntValue: UInt = g_variant_get_uint32(gVariantPtr)

    public actual val doubleValue: Double = g_variant_get_double(gVariantPtr)

    public actual val stringValue: String = g_variant_get_string(gVariantPtr, null)?.toKString() ?: ""

    public actual companion object {
        public actual fun fromString(value: String): Variant = Variant(g_variant_new_string(value))

        public actual fun fromBoolean(value: Boolean): Variant =
            Variant(g_variant_new_boolean(if (value) TRUE else FALSE))

        public actual fun fromUByte(value: UByte): Variant = Variant(g_variant_new_byte(value))

        public actual fun fromShort(value: Short): Variant = Variant(g_variant_new_int16(value))

        public actual fun fromUShort(value: UShort): Variant = Variant(g_variant_new_uint16(value))

        public actual fun fromInt(value: Int): Variant = Variant(g_variant_new_int32(value))

        public actual fun fromUInt(value: UInt): Variant = Variant(g_variant_new_uint32(value))

        public actual fun fromLong(value: Long): Variant = Variant(g_variant_new_int64(value))

        public actual fun fromDictEntry(key: Variant, value: Variant): Variant =
            Variant(g_variant_new_dict_entry(key.gVariantPtr, value.gVariantPtr))

        public fun fromPointer(ptr: CPointer<GVariant>?): Variant = Variant(ptr)
    }

    public actual fun fetchChildValue(value: Variant, index: ULong): Variant =
        Variant(g_variant_get_child_value(value.gVariantPtr, index))

    public actual fun lookupValue(dictionary: Variant, key: String, expectedType: VariantType): Variant? {
        val ptr = g_variant_lookup_value(dictionary = dictionary.gVariantPtr, key = key,
            expected_type = expectedType.gVariantTypePtr)
        return if (ptr != null) Variant(ptr) else null
    }
}
