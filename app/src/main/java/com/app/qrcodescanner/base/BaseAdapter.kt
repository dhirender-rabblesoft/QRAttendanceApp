package com.app.qrcodescanner.base

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.app.qrcodescanner.extension.inflate
import com.app.qrcodescanner.listner.RecycleItemViewCallback

abstract class BaseAdapter<T>(@LayoutRes private val view: Int) :
    RecyclerView.Adapter<BaseAdapter.IViewHolder>() {
    protected val list: ArrayList<T> = arrayListOf()
    var itemClickCallback: RecycleItemViewCallback<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IViewHolder {
        return getViewHolder(parent, view, viewType)
    }

    protected open fun getViewHolder(parent: ViewGroup?, vieww: Int, viewType: Int): IViewHolder {
        val view = parent?.inflate(vieww)
        return IViewHolder(view!!)
    }

    override fun getItemCount() = list.size

    abstract override fun onBindViewHolder(holder: IViewHolder, position: Int)

    fun addToList(list: List<T>) {
        if (list != null) {
            val previousRange = list.size
            this.list.addAll(list)
            val newRange = list.size
            notifyItemChanged(previousRange, newRange)
        }
    }

    fun addNewList(list: List<T>) {
        if (list != null) {
            this@BaseAdapter.list.clear()
            this@BaseAdapter.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun addtoList(item: T): Int {
        list.add(item)
        val insertedAt = list.size - 1
        notifyItemInserted(insertedAt)
        return insertedAt
    }

    fun addNewList(list: Iterable<T>?) {
        if (list != null) {
            this@BaseAdapter.list.clear()
            this@BaseAdapter.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun addMoreItems(items: List<T>?) {
        val previousRange = list.size
        items?.let { list.addAll(items) }
        val newRange = list.size
        notifyItemRangeInserted(previousRange, newRange)
    }

    fun addMoreItemsAtStart(items: List<T>) {
        val previousRange = list.size
        list.addAll(items)
        val newRange = list.size
        notifyItemRangeInserted(previousRange, newRange)
    }

    fun addMoreItemsAtStart2(items: List<T>) {
        val previousRange = list.size

        list.addAll(items)
        val newRange = list.size
        notifyItemRangeInserted(0, newRange)
    }

    fun appendAtStart(item: T) {
        list.add(0, item)
        notifyItemInserted(0)
    }

    fun getOriginalList(): ArrayList<T> {
        return list
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun removeAll() {
        list.clear()
        notifyDataSetChanged()
    }

    class IViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}