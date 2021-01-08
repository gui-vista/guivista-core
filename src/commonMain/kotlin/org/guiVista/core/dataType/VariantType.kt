package org.guiVista.core.dataType

import org.guiVista.core.Closable

/** Provides type information for a [Variant]. */
public expect class VariantType : Closable {
    /**
     * Determines the key type of a dictionary entry type. This property may only be used with a dictionary entry type.
     * Other than the additional restriction, this call is equivalent to [firstItem].
     */
    public val dictKey: VariantType?

    /**
     * Determines the value type of a dictionary entry type. This property may only be used with a dictionary entry
     * type.
     */
    public val dictValue: VariantType?

    /**
     * Determines the first item type of a tuple or dictionary entry type. This property may only be used with tuple,
     * or dictionary entry types, but must not be used with the generic tuple type `G_VARIANT_TYPE_TUPLE`. In the case
     * of a dictionary entry type, this returns the type of the key. The return value *null* is returned in case of
     * type being `G_VARIANT_TYPE_UNIT`.
     *
     * This property together with [nextItem] provides an iterator interface over tuple, and dictionary entry types.
     */
    public val firstItem: VariantType?

    /**
     * Determines the next item type of a tuple or dictionary entry type. Note that this [VariantType] must be the
     * result of a previous call to [firstItem] or [nextItem]. If called on the key type of a dictionary entry then
     * this call returns the value type. If called on the value type of a dictionary entry then this call returns
     * *null*.
     *
     * For tuples *null* is returned when type is the last item in a tuple.
     */
    public val nextItem: VariantType?

    /**
     * Determines the number of items contained in a tuple or dictionary entry type. This property may only be used
     * with tuple or dictionary entry types, but **MUST** not be used with the generic tuple type
     * `G_VARIANT_TYPE_TUPLE`. In the case of a dictionary entry type, this function will always return *2*.
     */
    public val totalItems: ULong

    /**
     * Determines if the given type is definite (ie: not indefinite). A type is definite if its Type String doesn't
     * contain any indefinite type characters ('*', *?*, or *r*).
     */
    public val isDefinite: Boolean

    /**
     * Determines if the given type is a container type. Container types are any array, maybe, tuple, or dictionary
     * entry types plus the variant type. This property returns *true* for any indefinite type for which every definite
     * subtype is a container - `G_VARIANT_TYPE_ARRAY`, for example.
     */
    public val isContainer: Boolean

    /**
     * Determines if the given type is a basic type. Basic types are booleans, bytes, integers, doubles, strings,
     * object paths and signatures. Only a basic type may be used as the key of a dictionary entry. This property
     * returns *false* for all indefinite types except `G_VARIANT_TYPE_BASIC`.
     */
    public val isBasic: Boolean

    /**
     * Determines if the given type is a maybe type. This is true if the type string for type starts with an *m*.
     * This property returns *true* for any indefinite type for which every definite subtype is a maybe type -
     * `G_VARIANT_TYPE_MAYBE`, for example.
     */
    public val isMaybe: Boolean

    /**
     * Determines if the given type is an array type. This is true if the type string for type starts with an *a*. This
     * property returns *true* for any indefinite type for which every definite subtype is an array type -
     * `G_VARIANT_TYPE_ARRAY`, for example.
     */
    public val isArray: Boolean

    /**
     * Determines if the given type is a tuple type. This is true if the type string for type starts with a *(* or if
     * type is `G_VARIANT_TYPE_TUPLE`. This property returns *true* for any indefinite type for which every definite
     * subtype is a tuple type - `G_VARIANT_TYPE_TUPLE`, for example.
     */
    public val isTuple: Boolean

    /**
     * Determines if the given type is a dictionary entry type. This is true if the type string for type starts with a
     * *{*. This function returns *true* for any indefinite type for which every definite subtype is a dictionary entry
     * type - `G_VARIANT_TYPE_DICT_ENTRY`, for example.
     */
    public val isDictEntry: Boolean

    /** Determines if the given type is the variant type. */
    public val isVariant: Boolean

    /**
     * Determines the element type of an array or maybe type. This function may only be used with array, or maybe types.
     */
    public val elementType: VariantType

    /**
     * Returns the length of the Type String corresponding to the given type. This function **MUST** be used to
     * determine the valid extent of the memory region returned by `g_variant_type_peek_string()`.
     */
    public val stringLength: ULong

    public companion object {
        /**
         * Checks if [str] is a valid [Variant] Type String. This call is equivalent to calling
         * `g_variant_type_string_scan()`, and confirming that the following character is a null terminator.
         * @param str A String representing a type.
         * @return A value of *true* if [str] is exactly one valid Type String.
         */
        public infix fun stringIsValid(str: String): Boolean

        /**
         * Constructs the type corresponding to a maybe instance containing type type or Nothing. It is appropriate to
         * call `g_variant_type_free()` on the return value.
         * @return A new maybe [VariantType].
         */
        public fun fromMaybe(element: VariantType): VariantType

        /**
         * Constructs the type corresponding to a maybe instance containing type type. It is appropriate to
         * call `g_variant_type_free()` on the return value.
         * @return A new array [VariantType].
         */
        public fun fromArray(element: VariantType): VariantType

        /**
         * Constructs the type corresponding to a dictionary entry with a key of type key and a value of type value. It
         * is appropriate to call `g_variant_type_free()` on the return value.
         * @param key A basic [VariantType].
         * @param value A [VariantType].
         * @return A new dictionary entry [VariantType].
         */
        public fun fromDictEntry(key: VariantType, value: VariantType): VariantType
    }

    /**
     * Makes a copy of this [VariantType].
     * @return A new [VariantType]
     */
    public fun copy(): VariantType

    /**
     * Returns a newly allocated copy of the Type String corresponding to type. The returned String is null terminated.
     * It is appropriate to call `g_free()` on the return value.
     */
    public fun dupString(): String

    /**
     * Checks if this [VariantType] is a subtype of supertype. This function returns *true* if this [VariantType] is a
     * subtype of [superType]. All types are considered to be subtypes of themselves. Aside from that only indefinite
     * types can have subtypes.
     * @param superType The super type.
     * @return A value of *true* if this [VariantType] is a subtype of [superType].
     */
    public fun isSubTypeOf(superType: VariantType): Boolean
}
