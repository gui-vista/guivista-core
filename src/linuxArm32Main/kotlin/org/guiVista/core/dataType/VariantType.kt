package org.guiVista.core.dataType

import glib2.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import org.guiVista.core.Closable

public actual class VariantType private constructor(variantTypePtr: CPointer<GVariantType>?) : Closable {
    public val gVariantTypePtr: CPointer<GVariantType>? = variantTypePtr

    public actual val dictKey: VariantType?
        get() {
            val ptr = g_variant_type_key(gVariantTypePtr)
            return if (ptr != null) VariantType(ptr) else null
        }

    public actual val dictValue: VariantType?
        get() {
            val ptr = g_variant_type_value(gVariantTypePtr)
            return if (ptr != null) VariantType(ptr) else null
        }

    public actual val firstItem: VariantType?
        get() {
            val ptr = g_variant_type_first(gVariantTypePtr)
            return if (ptr != null) VariantType(ptr) else null
        }

    public actual val nextItem: VariantType?
        get() {
            val ptr = g_variant_type_next(gVariantTypePtr)
            return if (ptr != null) VariantType(ptr) else null
        }

    public actual val totalItems: ULong
        get() = g_variant_type_n_items(gVariantTypePtr).toULong()

    public actual val isDefinite: Boolean
        get() = g_variant_type_is_definite(gVariantTypePtr) == TRUE

    public actual val isContainer: Boolean
        get() = g_variant_type_is_container(gVariantTypePtr) == TRUE

    public actual val isBasic: Boolean
        get() = g_variant_type_is_basic(gVariantTypePtr) == TRUE

    public actual val isMaybe: Boolean
        get() = g_variant_type_is_maybe(gVariantTypePtr) == TRUE

    public actual val isArray: Boolean
        get() = g_variant_type_is_array(gVariantTypePtr) == TRUE

    public actual val isTuple: Boolean
        get() = g_variant_type_is_tuple(gVariantTypePtr) == TRUE

    public actual val isDictEntry: Boolean
        get() = g_variant_type_is_dict_entry(gVariantTypePtr) == TRUE

    public actual val isVariant: Boolean
        get() = g_variant_type_is_variant(gVariantTypePtr) == TRUE

    public actual val elementType: VariantType
        get() = VariantType(g_variant_type_element(gVariantTypePtr))

    public actual val stringLength: ULong
        get() = g_variant_type_get_string_length(gVariantTypePtr).toULong()

    public actual companion object {
        public actual infix fun stringIsValid(str: String): Boolean =
            g_variant_type_string_is_valid(str) == TRUE

        public actual fun fromMaybe(element: VariantType): VariantType =
            VariantType(g_variant_type_new_maybe(element.gVariantTypePtr))

        public actual fun fromArray(element: VariantType): VariantType =
            VariantType(g_variant_type_new_array(element.gVariantTypePtr))

        public actual fun fromDictEntry(key: VariantType, value: VariantType): VariantType =
            VariantType(g_variant_type_new_dict_entry(key.gVariantTypePtr, value.gVariantTypePtr))

        public fun fromPointer(ptr: CPointer<GVariantType>?): VariantType = VariantType(ptr)
    }

    public actual fun copy(): VariantType = VariantType(g_variant_type_copy(gVariantTypePtr))

    public actual fun dupString(): String = g_variant_type_dup_string(gVariantTypePtr)?.toKString() ?: ""

    public actual fun isSubTypeOf(superType: VariantType): Boolean =
        g_variant_type_is_subtype_of(gVariantTypePtr, superType.gVariantTypePtr) == TRUE

    override fun close() {
        g_variant_type_free(gVariantTypePtr)
    }
}
