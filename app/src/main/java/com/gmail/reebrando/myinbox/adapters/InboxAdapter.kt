package com.gmail.reebrando.myinbox.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import com.gmail.reebrando.myinbox.ItemRowListener
import com.gmail.reebrando.myinbox.R
import com.gmail.reebrando.myinbox.models.Inbox

class InboxAdapter(context: Context, toDoItemList: MutableList<Inbox>) : BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList = toDoItemList
    private var rowListener: ItemRowListener = context as ItemRowListener

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val objectId: String = itemList.get(position).objectId as String
        val itemText: String = itemList.get(position).itemText as String
        val done: Boolean = itemList.get(position).done as Boolean
        val view: View
        val vh: ListRowHolder

        if (convertView == null) {
            view = mInflater.inflate(R.layout.row_items, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        vh.label.text = itemText
        vh.isDone.isChecked = done

        vh.isDone.setOnClickListener {
            rowListener.modifyItemState(objectId, !done) }
        vh.ibDeleteObject.setOnClickListener {
            rowListener.onItemDelete(objectId) }

        return view
    }

    override fun getItem(index: Int): Any {
        return itemList.get(index)
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getCount(): Int {
        return itemList.size
    }

    private class ListRowHolder(row: View?) {
        val label: TextView = row!!.findViewById<TextView>(R.id.tv_item_text) as TextView
        val isDone: CheckBox = row!!.findViewById<CheckBox>(R.id.cb_item_is_done) as CheckBox
        val ibDeleteObject: ImageButton = row!!.findViewById<ImageButton>(R.id.iv_cross) as ImageButton
    }
}