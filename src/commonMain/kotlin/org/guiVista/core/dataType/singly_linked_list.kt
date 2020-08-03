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

    fun copy(): SinglyLinkedList

    /** Reverses a list. */
    fun reverse()

    /**
     * Gets the position of the given element in the list (starting from 0).
     * @param listLink An element in the list.
     * @return The position of the element in the list, or *-1* if the element isn't found.
     */
    fun position(listLink: SinglyLinkedList): Int

    /**
     * Adds a singly linked list onto the end of this list. Note that the elements of the added list are not copied.
     * They are used directly.
     */
    fun concat(list: SinglyLinkedList)

    /**
     * Gets the last element in this list. This function iterates over the whole list.
     * @return The last element in this list, or *null* if the list has no elements.
     */
    fun last(): SinglyLinkedList?

    /**
     * Gets the element at the given [position][pos] in this list. This function is based on the
     * [g_slist_nth](https://developer.gnome.org/glib/2.64/glib-Singly-Linked-Lists.html#g-slist-nth) function.
     * @param pos The position of the element, counting from *0*.
     * @return The element, or *null* if the position is off the end of this list.
     */
    fun elementAt(pos: UInt): SinglyLinkedList?
}
