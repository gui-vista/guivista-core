package org.guiVista.core

import glib2.*
import kotlinx.cinterop.*

public actual class Error private constructor(errorPtr: CPointer<GError>?) : Closable {
    public val gErrorPtr: CPointer<GError>? = errorPtr

    public actual companion object {
        /** Creates a new [Error] instance from a errorPtr. */
        public fun fromErrorPointer(errorPtr: CPointer<GError>): Error = Error(errorPtr)

        public actual fun fromLiteral(domain: UInt, code: Int, message: String): Error =
            Error(g_error_new_literal(domain = domain, code = code, message = message))

        public actual fun propagate(dest: Error?, src: Error) {
            g_propagate_error(if (dest != null) cValuesOf(dest.gErrorPtr) else null, src.gErrorPtr)
        }
    }

    public actual fun changeLiteral(domain: UInt, code: Int, message: String) {
        g_set_error_literal(err = cValuesOf(gErrorPtr), domain = domain, code = code, message = message)
    }

    override fun close() {
        g_error_free(gErrorPtr)
    }

    public actual fun matches(domain: UInt, code: Int): Boolean =
        g_error_matches(error = gErrorPtr, domain = domain, code = code) == TRUE
}
