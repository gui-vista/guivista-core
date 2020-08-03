package org.guiVista.core.dataType

import glib2.*
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed
import kotlinx.cinterop.reinterpret
import org.guiVista.core.Closable

actual class DoublyLinkedList(listPtr: CPointer<GList>? = null) : Closable {
    private var _gListPtr = listPtr ?: g_list_alloc()
    val gListPtr: CPointer<GList>?
        get() = _gListPtr
    actual val length: UInt
        get() = g_list_length(_gListPtr)
    var data: CPointer<*>?
        get() = _gListPtr?.pointed?.data
        set(value) {
            _gListPtr?.pointed?.data = value
        }
    actual val next: DoublyLinkedList?
        get() {
            val tmp = _gListPtr?.pointed
            return if (tmp != null) DoublyLinkedList(tmp.next?.reinterpret()) else null
        }
    actual val prev: DoublyLinkedList?
        get() {
            val tmp = _gListPtr?.pointed
            return if (tmp != null) DoublyLinkedList(tmp.prev?.reinterpret()) else null
        }
    actual val first: DoublyLinkedList?
        get() {
            val tmp = g_list_first(_gListPtr)
            return if (tmp != null) DoublyLinkedList(tmp) else null
        }
    actual val last: DoublyLinkedList?
        get() {
            val tmp = g_list_last(_gListPtr)
            return if (tmp != null) DoublyLinkedList(tmp) else null
        }

    /**
     * Frees up the [DoublyLinkedList] instance. Note that only the list is freed. This function **MUST** be called to
     * prevent memory leaks when you are finished with the [DoublyLinkedList] instance.
     */
    override fun close() {
        g_list_free(_gListPtr)
        _gListPtr = null
    }

    /**
     * Adds a new element on to the end of the list.
     * @param data The data for the new element.
     * @see append
     */
    operator fun plusAssign(data: CPointer<*>?) {
        append(data)
    }

    /**
     * Adds a new element on to the end of the list. The return value is the new start of the list, which may have
     * changed so make sure you store the new value. Note that the entire list needs to be traversed to find the end,
     * which is inefficient when adding multiple elements. A common idiom to avoid the inefficiency is to prepend the
     * elements, and reverse the list when all elements have been added.
     * @param data The data for the new element.
     */
    fun append(data: CPointer<*>?) {
        g_list_append(_gListPtr, data)
    }

    /**
     * Removes an element from a list.
     * @param data The data of the element to remove.
     * @see remove
     */
    operator fun minusAssign(data: CPointer<*>?) {
        remove(data)
    }

    /**
     * Removes an element from a list. If two elements contain the same data, only the first is removed. If none of the
     * elements contain the data the list is unchanged.
     * @param data The data of the element to remove.
     */
    fun remove(data: CPointer<*>?) {
        g_list_remove(_gListPtr, data)
    }

    /**
     * Adds a new element on to the start of the list. The return value is the new start of the list, which may have
     * changed so make sure you store the new value.
     * @param data The data for the new element.
     */
    fun prepend(data: CPointer<*>?) {
        g_list_prepend(_gListPtr, data)
    }

    /**
     * Removes all list nodes with data equal to data. Returns the new head of the list. Contrast with [remove], which
     * removes only the first node matching the given data.
     * @param data The data to remove.
     */
    fun removeAll(data: CPointer<*>?) {
        g_list_remove_all(_gListPtr, data)
    }

    /**
     * Inserts a new element into the list at the given position.
     * @param data The data for the new element.
     * @param position The position to insert the element. If this is negative, or is larger than the number of
     * elements in the list, then the new element is added on to the end of the list.
     */
    fun insert(data: CPointer<*>?, position: Int) {
        g_list_insert(list = _gListPtr, data = data, position = position)
    }

    /**
     * Inserts a node before sibling containing data .
     * @param sibling A node to insert data before.
     * @param data The data to put in the newly inserted node.
     */
    fun insertBefore(sibling: DoublyLinkedList, data: CPointer<*>?) {
        g_list_insert_before(list = _gListPtr, sibling = sibling._gListPtr, data = data)
    }

    actual fun reverse() {
        g_list_reverse(_gListPtr)
    }

    /**
     * Finds the element in a GSList which contains the given data.
     * @param data The element data to find.
     * @see find
     */
    operator fun contains(data: CPointer<*>?): Boolean = find(data) != null

    /**
     * Finds the element in a GSList which contains the given data.
     * @param data The element data to find.
     * @return The found list element, or *null* if it isn't found.
     */
    fun find(data: CPointer<*>?): DoublyLinkedList? {
        val tmp = g_list_find(_gListPtr, data)
        return if (tmp != null) DoublyLinkedList(tmp) else null
    }

    actual fun position(listLink: DoublyLinkedList): Int = g_list_position(_gListPtr, listLink._gListPtr)

    /**
     * Gets the position of the element containing the given data (starting from 0).
     * @param data The data to find.
     * @return The index of the element containing the data, or *-1* if the data isn't found.
     */
    fun index(data: CPointer<*>?): Int = g_list_index(_gListPtr, data)

    /**
     * Inserts a new element into the list using the given comparison function to determine its position. If you are
     * adding many new elements to a list, and the number of new elements is much larger than the length of the list,
     * then use [prepend] to add the new items, and sort the list afterwards with [sort].
     * @param data The data for the new element.
     * @param func The function to compare elements in the list. It should return a number > *0* if the first parameter
     * comes after the second parameter in the sort order.
     */
    fun insertSorted(data: gpointer, func: GCompareFunc) {
        g_list_insert_sorted(list = _gListPtr, data = data, func = func)
    }

    actual fun removeLink(link: DoublyLinkedList) {
        g_list_remove_link(_gListPtr, link._gListPtr)
    }

    actual fun deleteLink(link: DoublyLinkedList) {
        g_list_delete_link(_gListPtr, link._gListPtr)
    }

    /**
     * Removes all list nodes with data equal to [data]. Returns the new head of the list. Contrast with [remove] which
     * removes only the first node matching the given data.
     * @param data Data to remove.
     */
    fun removeAll(data: COpaquePointer) {
        g_list_remove_all(_gListPtr, data)
    }

    /**
     * Convenience method which frees all the memory used by this list, and calls free_func on every element's data.
     * **Note:** *free_func* must **NOT** modify the list (eg, by removing the freed element from it).
     * @param freeFunc The function to be called to free each element's data.
     */
    fun freeFull(freeFunc: GDestroyNotify) {
        g_list_free_full(_gListPtr, freeFunc)
    }

    actual fun copy(): DoublyLinkedList = DoublyLinkedList(g_list_copy(_gListPtr))

    /**
     * Makes a full (deep) copy of this list. In contrast with [copy], this function uses func to make a copy of each
     * list element, in addition to copying the list container itself.
     * @param func A copy function used to copy every element in the list. This function takes two arguments, the data
     * to be copied and a [userData] pointer. On common processor architectures it's safe to pass *null* as [userData]
     * if the copy function takes only one argument.
     * @param userData User data passed to the []copy function][func], or *null*.
     * @return The start of the new list that holds a full copy of this list. Use [freeFull] to free it.
     */
    fun copyDeep(func: GCopyFunc, userData: gpointer): DoublyLinkedList =
        DoublyLinkedList(g_list_copy_deep(list = _gListPtr, func = func, user_data = userData))

    /**
     * Sorts this list using the given comparison function. The algorithm used is a stable sort.
     * @param compareFunc The comparison function used to sort this list. This function is passed the data from two
     * elements of this list, and should return *0* if they are equal, a negative value if the first element comes
     * before the second, or a positive value if the first element comes after the second.
     */
    fun sort(compareFunc: GCompareFunc) {
        g_list_sort(_gListPtr, compareFunc)
    }

    /**
     * Inserts a new element into this list, using the given comparison function to determine its position. If you are
     * adding many new elements to a list, and the number of new elements is much larger than the length of the list,
     * then use [prepend] to add the new items, and sort the list afterwards with [sort].
     * @param data The data for the new element.
     * @param func The function to compare elements in this list. It should return a number > *0* if the first
     * parameter comes after the second parameter in the sort order.
     * @param userData User data to pass to comparison function.
     */
    fun insertSortedWithData(data: gpointer, func: GCompareDataFunc, userData: gpointer) {
        g_list_insert_sorted_with_data(list = _gListPtr, func = func, data = data, user_data = userData)
    }

    /**
     * Like [sort] but the comparison function accepts a [user data][userData] argument.
     * @param compareFunc Comparison function.
     * @param userData User data to pass to [comparison function][compareFunc].
     */
    fun sortWithData(compareFunc: GCompareDataFunc, userData: gpointer) {
        g_list_sort_with_data(list = _gListPtr, compare_func = compareFunc, user_data = userData)
    }

    actual fun concat(list: DoublyLinkedList) {
        g_list_concat(_gListPtr, list._gListPtr)
    }

    actual fun elementAt(pos: UInt): DoublyLinkedList? {
        val tmp = g_list_nth(_gListPtr, pos)
        return if (tmp != null) DoublyLinkedList(tmp) else null
    }

    /**
     * Gets the data of the element at the given position. This iterates over the list until it reaches the n -th
     * position. If you intend to iterate over every element, it is better to use a for loop as described in the GList
     * introduction. This function is based on the
     * [g_list_nth_data](https://developer.gnome.org/glib/2.64/glib-Doubly-Linked-Lists.html#g-list-nth-data) function.
     * @param pos The position of the element.
     * @return The element's data, or *null* if the position is off the end of this list.
     */
    fun dataAt(pos: UInt): gpointer? = g_list_nth_data(_gListPtr, pos)

    /**
     * Finds an element in this list using a supplied function to find the desired element. It iterates over the list,
     * calling the given function which should return *0* when the desired element is found.
     * @param data User data passed to the [function][func].
     * @param func The function to call for each element. It should return *0* when the desired element is found. The
     * function takes two COpaquePointer arguments, the list element's data as the first argument, and the given user
     * data.
     * @return The found doubly linked list element, or *null* if it isn't found.
     */
    fun findCustom(data: COpaquePointer, func: GCompareFunc): DoublyLinkedList? {
        val tmp = g_list_find_custom(list = _gListPtr, data = data, func = func)
        return if (tmp != null) DoublyLinkedList(tmp) else null
    }

}

fun doublyLinkedList(listPtr: CPointer<GList>? = null, init: DoublyLinkedList.() -> Unit): DoublyLinkedList {
    val list = DoublyLinkedList(listPtr)
    list.init()
    return list
}
