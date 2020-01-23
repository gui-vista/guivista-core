package org.guiVista.core.dataType

import org.guiVista.core.Closable

/**
 * A linked list that can be iterated over in both directions. Remember to call [close] when you are finished with a
 * DoublyLinkedList instance. Maps to [GList](https://developer.gnome.org/glib/stable/glib-Doubly-Linked-Lists.html)
 * GLib data type.
 */
expect class DoublyLinkedList : Closable {
    /** The number of elements in a list. */
    val length: UInt
    val next: DoublyLinkedList?
    val prev: DoublyLinkedList?
}
