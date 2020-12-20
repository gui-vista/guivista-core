package org.guiVista.core

/** Allows a resource to be closed. */
public interface Closable {
    /** Closes the resource. May also close other child resources. */
    public fun close()
}
