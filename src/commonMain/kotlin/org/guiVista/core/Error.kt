package org.guiVista.core

/** A system for reporting a error. */
public expect class Error : Closable {
    public companion object {
        /**
         * Creates a new [Error] instance. Unlike `g_error_new()`, [message] isn't a `printf` style format string. Use
         * this function if [message] contains text you don't have control over, that could include `printf` escape
         * sequences.
         * @param domain Error domain.
         * @param code Error code.
         * @param message Error message.
         * @return A new [Error] instance.
         */
        public fun fromLiteral(domain: UInt, code: Int, message: String): Error

        /**
         * If [dest] is *null* then free [src] otherwise move [src] into [dest]. The error variable [dest] points to
         * **MUST** be *null*. Note that [src] is no longer valid after this call. If you want to keep using the same
         * [Error], you need to set it to *null* after calling this function on it.
         */
        public fun propagate(dest: Error?, src: Error)
    }

    /**
     * Alters the [error's][Error] literal. Use this function if [message] contains text you don't have control
     * over. That could include `printf()` escape sequences.
     * @param domain The error domain.
     * @param code The error code.
     * @param message The error message.
     */
    public fun changeLiteral(domain: UInt, code: Int, message: String)

    /**
     * Checks to see if the error matches the [domain], and [code]. In particular when error is *null*, *false* will be
     * returned. If [domain] contains a FAILED (or otherwise generic) error code you should generally not check for it
     * explicitly, but should instead treat any not explicitly recognized error code as being equivalent to the FAILED
     * code. This way if the [domain] is extended in the future to provide a more specific error code for a certain
     * case, your code will still work.
     * @param domain An error domain.
     * @param code An error code.
     * @return If error matches [domain], and [code] then *true*, otherwise *false*.
     */
    public fun matches(domain: UInt, code: Int): Boolean
}
