package org.guiVista.core

import glib2.GConnectFlags
import glib2.g_signal_connect_data
import glib2.g_signal_handler_disconnect
import glib2.gpointer
import kotlinx.cinterop.*

private val emptyData by lazy { EmptyData() }
private val emptyDataRef = StableRef.create(emptyData)

/** Gets the C Pointer for EmptyData, which is used for supplying "empty" data when connecting a signal to a slot. **/
@Suppress("unused")
public fun fetchEmptyDataPointer(): COpaquePointer = emptyDataRef.asCPointer()

@Suppress("unused")
public actual fun disposeEmptyDataRef() {
    emptyDataRef.dispose()
}

private class EmptyData

/**
 * Connects a signal (event) to a slot (event handler). Note that all callback parameters must be primitive types or
 * nullable C pointers.
 * @param obj The object to use for connecting a [signal][signal] to a [slot][slot].
 * @param signal The name of the signal to connect to.
 * @param slot The slot to use for handling the signal.
 * @param data User data to pass through to the [slot]. By default no user data is passed through.
 * @param connectFlags The flags to use.
 * @return A handler ID > 0 for the [slot] **if** the connection is successful.
 */
@Suppress("unused")
public fun <F : CFunction<*>> connectGSignal(
    obj: CPointer<*>?,
    signal: String,
    slot: CPointer<F>,
    data: gpointer? = null,
    connectFlags: GConnectFlags = 0u
): UInt = g_signal_connect_data(
    instance = obj,
    detailed_signal = signal,
    c_handler = slot.reinterpret(),
    data = data,
    destroy_data = null,
    connect_flags = connectFlags
)

/**
 * Disconnects a signal (event) from a slot (event handler) on a [object][obj].
 * @param obj The GObject to use.
 * @param handlerId The handler ID to use.
 */
@Suppress("unused")
public fun disconnectGSignal(obj: CPointer<*>?, handlerId: UInt) {
    g_signal_handler_disconnect(obj, handlerId)
}
