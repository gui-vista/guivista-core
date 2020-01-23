package org.guiVista.core.dataType

import org.guiVista.core.Closable

/**
 * A singly linked list that can be iterated in one direction. Remember to call [close] when you are finished with a
 * SinglyLinkedList instance. Maps to [GSList](https://developer.gnome.org/glib/stable/glib-Singly-Linked-Lists.html)
 * GLib data type.
 */
expect class SinglyLinkedList : Closable {
    /** The number of elements in a list. */
    val length: UInt
    val next: SinglyLinkedList?
}
