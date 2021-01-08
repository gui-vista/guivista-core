package org.guiVista.core.dataType

/** Strongly typed value data type. */
public expect class Variant {
    /**
     * Determines the number of children in a container [Variant] instance. This includes variants, maybes, arrays,
     * tuples, and dictionary entries. It is an error to call this function on any other type of [Variant]. For
     * variants the return value is always *1*. For values with maybe types, it is always zero or one. For arrays it is
     * the length of the array. For tuples it is the number of tuple items (which depends only on the type). For
     * dictionary entries it is always *2*.
     *
     * Performance wise this property is O(1).
     */
    public val totalChildren: ULong

    public val booleanValue: Boolean

    public val uByteValue: UByte

    public val shortValue: Short

    public val uShortValue: UShort

    public val intValue: Int

    public val uIntValue: UInt

    public val doubleValue: Double

    public val stringValue: String

    public companion object {
        /**
         * Creates a String [Variant] with the contents of [value]. The String **MUST** be valid UTF-8, and **MUST** not
         * be *null*. To encode potentially null strings, use g_variant_new() with ms as the format string.
         * @param value The String to use as a value.
         * @return A floating reference to a new String [Variant] instance.
         */
        public fun fromString(value: String): Variant

        /**
         * Creates a new Boolean [Variant] instance - either *true* or *false*.
         * @param value A Boolean value.
         * @return A floating reference to a new Boolean [Variant] instance.
         */
        public fun fromBoolean(value: Boolean): Variant

        /**
         * Creates a new UByte [Variant] instance.
         * @param value A UByte value.
         * @return A floating reference to a new UByte [Variant] instance.
         */
        public fun fromUByte(value: UByte): Variant

        /**
         * Creates a new Short [Variant] instance.
         * @param value A Short value.
         * @return A floating reference to a new Short [Variant] instance.
         */
        public fun fromShort(value: Short): Variant

        /**
         * Creates a new UShort [Variant] instance.
         * @param value A UShort value.
         * @return A floating reference to a new UShort [Variant] instance.
         */
        public fun fromUShort(value: UShort): Variant

        /**
         * Creates a new Int [Variant] instance.
         * @param value A Int value.
         * @return A floating reference to a new Int [Variant] instance.
         */
        public fun fromInt(value: Int): Variant

        /**
         * Creates a new UInt [Variant] instance.
         * @param value A UInt value.
         * @return A floating reference to a new UInt [Variant] instance.
         */
        public fun fromUInt(value: UInt): Variant

        /**
         * Creates a new Long [Variant] instance.
         * @param value A Long value.
         * @return A floating reference to a new Long [Variant] instance.
         */
        public fun fromLong(value: Long): Variant

        /**
         * Creates a new dictionary entry [Variant]. The [key] must be a value of a basic type (ie: not a container).
         * If the [key] or [value] are floating references (see `g_variant_ref_sink()`) then the new instance takes
         * ownership of them as if via `g_variant_ref_sink()`.
         * @param key A basic [Variant] as the key.
         * @param value A [Variant] as the value.
         * @return A floating reference to a new dictionary entry [Variant].
         */
        public fun fromDictEntry(key: Variant, value: Variant): Variant
    }

    /**
     * Reads a child item out of a container [Variant] instance. This includes variants, maybes, arrays, tuples, and
     * dictionary entries. It is an error to call this function on any other type of [Variant]. Note it is an error if
     * [index] is greater than the number of child items in the container.
     *
     * The returned value is never floating. You should free it with `g_variant_unref()` when you're done with it. Note
     * that values borrowed from the returned child are not guaranteed to still be valid after the child is freed, even
     * if you still hold a reference to value, if value has not been serialised at the time this function is called. To
     * avoid this you can serialize value by calling `g_variant_get_data()` and optionally ignoring the return value.
     *
     * There may be implementation specific restrictions on deeply nested values, which would result in the unit tuple
     * being returned as the child value, instead of further nested children. [Variant] is guaranteed to handle nesting
     * up to at least 64 levels.
     *
     * Performance wise this function is O(1).
     * @see totalChildren
     */
    public fun fetchChildValue(value: Variant, index: ULong): Variant

    /**
     * Looks up a value in a dictionary [Variant]. This function works with dictionaries of the type a{s*}, and equally
     * well with type a{o*}, but we only further discuss the string case for sake of clarity. In the event that
     * dictionary has the type a{sv}, the [expectedType] String specifies what type of value is expected to be inside
     * of the variant. If the value inside the variant has a different type then *null* is returned. In the event that
     * dictionary has a value type other than v then [expectedType] must directly match the value type, and it is used
     * to unpack the value directly or an error occurs.
     *
     * In either case if [key] is not found in dictionary then *null* is returned. If the [key] is found, and the value
     * has the correct type then it is returned. However if [expectedType] was specified then any non null return value
     * will have this type. This function is currently implemented with a linear scan. If you plan to do many lookups
     * then `GVariantDict` may be more efficient.
     */
    public fun lookupValue(dictionary: Variant, key: String, expectedType: VariantType): Variant?
}
