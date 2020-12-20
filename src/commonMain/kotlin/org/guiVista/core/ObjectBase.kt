package org.guiVista.core

/** Root foundation for interfaces, and classes based on GObject. */
public interface ObjectBase : Closable {
    /**
     * Disconnects a signal (event) from a slot (event handler) on a object.
     * @param handlerId The handler ID to use.
     */
    public fun disconnectSignal(handlerId: ULong) {}
}
