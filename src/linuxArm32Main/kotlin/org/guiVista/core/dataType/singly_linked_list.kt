package org.guiVista.core.dataType

import glib2.*
import kotlinx.cinterop.*
import org.guiVista.core.Closable

public actual class SinglyLinkedList(listPtr: CPointer<GSList>? = null) : Closable {
    private var _gSListPtr = listPtr ?: g_slist_alloc()
    public val gSListPtr: CPointer<GSList>?
        get() = _gSListPtr
    public actual val length: UInt
        get() = g_slist_length(_gSListPtr)
    public var data: CPointer<*>?
        get() = _gSListPtr?.pointed?.data
        set(value) {
            _gSListPtr?.pointed?.data = value
        }
    public actual val next: SinglyLinkedList?
        get() {
            val tmp = _gSListPtr?.pointed
            return if (tmp != null) SinglyLinkedList(tmp.next?.reinterpret()) else null
        }

    /**
     * Adds a new element on to the end of the list.
     * @param data The data for the new element.
     * @see append
     */
    public operator fun plusAssign(data: CPointer<*>?) {
        append(data)
    }

    /**
     * Adds a new element on to the end of the list. The return value is the new start of the list, which may have
     * changed so make sure you store the new value. Note that the entire list needs to be traversed to find the end,
     * which is inefficient when adding multiple elements. A common idiom to avoid the inefficiency is to prepend the
     * elements, and reverse the list when all elements have been added.
     * @param data The data for the new element.
     */
    public fun append(data: CPointer<*>?) {
        g_slist_append(_gSListPtr, data)
    }

    /**
     * Removes an element from a list.
     * @param data The data of the element to remove.
     * @see remove
     */
    public operator fun minusAssign(data: CPointer<*>?) {
        remove(data)
    }

    /**
     * Removes an element from a list. If two elements contain the same data, only the first is removed. If none of the
     * elements contain the data the list is unchanged.
     * @param data The data of the element to remove.
     */
    public fun remove(data: CPointer<*>?) {
        g_slist_remove(_gSListPtr, data)
    }

    /**
     * Adds a new element on to the start of the list. The return value is the new start of the list, which may have
     * changed so make sure you store the new value.
     * @param data The data for the new element.
     */
    public fun prepend(data: CPointer<*>?) {
        g_slist_prepend(_gSListPtr, data)
    }

    /**
     * Frees up the [SinglyLinkedList] instance. Note that only the list is freed. This function **MUST** be called to
     * prevent memory leaks when you are finished with the [SinglyLinkedList] instance.
     */
    override fun close() {
        g_slist_free(_gSListPtr)
        _gSListPtr = null
    }

    /**
     * Removes all list nodes with data equal to data. Returns the new head of the list. Contrast with [remove], which
     * removes only the first node matching the given data.
     * @param data The data to remove.
     */
    public fun removeAll(data: CPointer<*>?) {
        g_slist_remove_all(_gSListPtr, data)
    }

    /**
     * Inserts a new element into the list at the given position.
     * @param data The data for the new element.
     * @param position The position to insert the element. If this is negative, or is larger than the number of
     * elements in the list, then the new element is added on to the end of the list.
     */
    public fun insert(data: CPointer<*>?, position: Int) {
        g_slist_insert(list = _gSListPtr, data = data, position = position)
    }

    /**
     * Inserts a node before sibling containing data .
     * @param sibling A node to insert data before.
     * @param data The data to put in the newly inserted node.
     */
    public fun <E : CPointed> insertBefore(sibling: SinglyLinkedList, data: CPointer<E>?) {
        g_slist_insert_before(slist = _gSListPtr, sibling = sibling._gSListPtr, data = data)
    }

    public actual fun reverse() {
        g_slist_reverse(_gSListPtr)
    }

    /**
     * Finds the element in a GSList which contains the given data.
     * @param data The element data to find.
     * @return A value of *true* if the element is found.
     * @see find
     */
    public operator fun contains(data: CPointer<*>?): Boolean = find(data) != null

    /**
     * Finds the element in a GSList which contains the given data.
     * @param data The element data to find.
     * @return The found list element, or *null* if it isn't found.
     */
    public fun find(data: CPointer<*>?): SinglyLinkedList? {
        val tmp = g_slist_find(_gSListPtr, data)
        return if (tmp != null) SinglyLinkedList(tmp) else null
    }

    public actual fun position(listLink: SinglyLinkedList): Int = g_slist_position(_gSListPtr, listLink._gSListPtr)

    /**
     * Gets the position of the element containing the given data (starting from 0).
     * @param data The data to find.
     * @return The index of the element containing the data, or *-1* if the data isn't found.
     */
    public fun index(data: CPointer<*>?): Int = g_slist_index(_gSListPtr, data)

    /**
     * Inserts a new element into the list using the given comparison [function][func] to determine its position.
     * @param data The data for the new element.
     * @param func The function to compare elements in the list. It should return a number > *0* if the first parameter
     * comes after the second parameter in the sort order.
     */
    public fun insertSorted(data: gpointer, func: GCompareFunc) {
        g_slist_insert_sorted(list = _gSListPtr, data = data, func = func)
    }

    /**
     * Removes an element from a GSList without freeing the element. The removed element's next link is set to *null*,
     * so that it becomes a self-contained list with one element. Removing arbitrary nodes from a singly-linked list
     * requires time that is proportional to the length of the list (ie. O(n)). If you find yourself using
     * [removeLink] frequently, you should consider a different data structure such as the
     * [doubly linked list][DoublyLinkedList].
     * @param link An element in the list.
     */
    public fun removeLink(link: CPointer<GSList>?) {
        g_slist_remove_link(_gSListPtr, link)
    }

    /**
     * Removes the node link_ from the list and frees it. Compare this to [removeLink] which removes the node without
     * freeing it. Removing arbitrary nodes from a singly linked list requires time that is proportional to the length
     * of the list (ie. O(n)). If you find yourself using [deleteLink] frequently, you should consider a different data
     * structure such as the [doubly linked list][DoublyLinkedList].
     * @param link Node to delete.
     */
    public fun deleteLink(link: CPointer<GSList>?) {
        g_slist_delete_link(_gSListPtr, link)
    }

    /**
     * Copies a singly linked list. Note that this is a *"shallow"* copy. If the list elements consist of pointers to
     * data, the pointers are copied but the actual data isn't.
     * @return A shallow copy of the list.
     * @see copyDeep
     */
    public actual fun copy(): SinglyLinkedList = SinglyLinkedList(g_slist_copy(_gSListPtr))

    /**
     * Makes a full (deep) copy of a GSList. In contrast with [copy], this function uses [func] to make a copy of each
     * list element, in addition to copying the list container itself.
     * @param func A copy function used to copy every element in the list. Takes two arguments, the data to be copied,
     * and a userData pointer. On common processor architectures it's safe to pass *null* as userData, if the copy
     * function takes only one argument.
     * @param userData User data passed to the [copy function][func] or *null*.
     * @return A full copy of the list. Use [freeFull] to free it.
     */
    public fun copyDeep(func: GCopyFunc, userData: gpointer): SinglyLinkedList =
        SinglyLinkedList(g_slist_copy_deep(list = _gSListPtr, user_data = userData, func = func))

    /**
     * Convenience method which frees all the memory used by a singly linked list, and calls the specified destroy
     * function on every element's data. The [freeFunc] parameter must **NOT** modify the list (eg, by removing the freed
     * element from it).
     * @param freeFunc The function to be called to free each element's data.
     */
    public fun freeFull(freeFunc: GDestroyNotify) {
        g_slist_free_full(_gSListPtr, freeFunc)
    }

    /**
     * Inserts a new element into the list using the given comparison function to determine its position.
     * @param data The data forthe new element.
     * @param func The function to compare elements in the list. It should return a number > *0* if the first parameter
     * comes after the second parameter in the sort order.
     * @param userData Data to pass to comparison function.
     */
    public fun insertSortedWithData(data: gpointer, func: GCompareDataFunc, userData: gpointer) {
        g_slist_insert_sorted_with_data(list = _gSListPtr, data = data, func = func, user_data = userData)
    }

    /**
     * Sorts a singly linked list using the given comparison function. The algorithm used is a stable sort.
     * @param compareFunc The comparison function used to sort the list. This function is passed the data from 2
     * elements of the list and should return *0* if they are equal, a negative value if the first element comes before
     * the second, or a positive value if the first element comes after the second.
     */
    public fun sort(compareFunc: GCompareFunc) {
        g_slist_sort(_gSListPtr, compareFunc)
    }

    /**
     * Like [sort] but this function accepts a [user data argument][userData].
     * @param compareFunc Comparision function.
     * @param userData Data to pass to comparison function.
     */
    public fun sortWithData(compareFunc: GCompareDataFunc, userData: gpointer) {
        g_slist_sort_with_data(list = _gSListPtr, compare_func = compareFunc, user_data = userData)
    }

    public actual fun concat(list: SinglyLinkedList) {
        g_slist_concat(_gSListPtr, list._gSListPtr)
    }

    public actual fun last(): SinglyLinkedList? {
        val tmp = g_slist_last(_gSListPtr)
        return if (tmp != null) SinglyLinkedList(tmp) else null
    }

    public actual fun elementAt(pos: UInt): SinglyLinkedList? {
        val tmp = g_slist_nth(_gSListPtr, pos)
        return if (tmp != null) SinglyLinkedList(tmp) else null
    }

    /**
     * Gets the data of the element at the given [position][pos]. This function is based on the
     * [g_slist_nth_data](https://developer.gnome.org/glib/2.64/glib-Singly-Linked-Lists.html#g-slist-nth-data)
     * function.
     * @param pos The position of the element.
     * @return The element's data, or *null* if the position is off the end of this list.
     */
    public fun dataAt(pos: UInt): gpointer? = g_slist_nth_data(_gSListPtr, pos)

    /**
     * Finds an element in this list using a supplied function to find the desired element. It iterates over the list,
     * calling the given function which should return *0* when the desired element is found.
     * @param data User data passed to the function.
     * @param func The function to call for each element. It should return *0* when the desired element is found. The
     * function takes two COpaquePointer arguments, the list element's data as the first argument, and the given user
     * data.
     */
    public fun findCustom(data: COpaquePointer, func: GCompareFunc): SinglyLinkedList? {
        val tmp = g_slist_find_custom(list = _gSListPtr, data = data, func = func)
        return if (tmp != null) SinglyLinkedList(tmp) else null
    }
}

public fun singlyLinkedList(listPtr: CPointer<GSList>? = null, init: SinglyLinkedList.() -> Unit): SinglyLinkedList {
    val list = SinglyLinkedList(listPtr)
    list.init()
    return list
}
