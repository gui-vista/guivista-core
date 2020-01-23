package org.guiVista.core

import kotlinx.cinterop.*

private val emptyData by lazy { EmptyData() }
private val emptyDataRef = StableRef.create(emptyData)

/** Gets the C Pointer for EmptyData, which is used for supplying "empty" data when connecting a signal to a slot. **/
@Suppress("unused")
fun fetchEmptyDataPointer(): COpaquePointer = emptyDataRef.asCPointer()

/** Frees up the empty data reference. */
fun disposeEmptyDataRef() {
    emptyDataRef.dispose()
}

private class EmptyData
