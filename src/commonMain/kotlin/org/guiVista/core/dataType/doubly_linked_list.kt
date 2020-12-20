package org.guiVista.core.dataType

import org.guiVista.core.Closable

/**
 * A linked list that can be iterated over in both directions. Remember to call [close] when you are finished with a
 * DoublyLinkedList instance. Maps to [GList](https://developer.gnome.org/glib/stable/glib-Doubly-Linked-Lists.html)
 * GLib data type.
 */
public expect class DoublyLinkedList : Closable {
    /** The number of elements in a list. */
    public val length: UInt
    public val next: DoublyLinkedList?
    public val prev: DoublyLinkedList?

    /** Fetches the first element in this list. */
    public val first: DoublyLinkedList?

    /** Fetches the last element in this list. */
    public val last: DoublyLinkedList?

    /** Reverses a list. */
    public fun reverse()

    /**
     * Gets the position of the given element in the list (starting from 0).
     * @param listLink An element in the list.
     * @return The position of the element in the list, or *-1* if the element isn't found.
     */
    public fun position(listLink: DoublyLinkedList): Int

    /**
     * Removes an element from a GList without freeing the element. The removed element's [prev], and [next] links are
     * set to *null*, so that it becomes a self contained list with one element.
     * @param link An element in this list.
     */
    public fun removeLink(link: DoublyLinkedList)

    /**
     * Removes the [node link][link] from the list, and frees it. Compare this to [removeLink] which removes the node
     * without freeing it.
     * @param link Node to delete from this list.
     */
    public fun deleteLink(link: DoublyLinkedList)

    /**
     * Copies this list. **Note:** this is a *"shallow"* copy. If the list elements consist of pointers to data, the
     * pointers are copied but the actual data is not. See copyDeep function if you need to copy the data as well.
     * @return The start of the new list that holds the same data as this list.
     */
    public fun copy(): DoublyLinkedList

    /**
     * Adds a doubly linked list onto the end of this list. **Note:** the elements of the this list are not copied.
     * They are used directly.
     * @param list The list to add to the end of this list, this must point to the top of the list.
     */
    public fun concat(list: DoublyLinkedList)

    /**
     * Gets the element at the given position in this list. This iterates over the list until it reaches the n -th
     * position. If you intend to iterate over every element, it is better to use a for loop as described in the GList
     * introduction. This function is based on the
     * [g_list_nth](https://developer.gnome.org/glib/2.64/glib-Doubly-Linked-Lists.html#g-list-nth) function.
     * @param pos The position of the element, counting from 0.
     * @return The element, or *null* if the position is off the end of the GList.
     */
    public fun elementAt(pos: UInt): DoublyLinkedList?
}
