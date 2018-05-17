package com.gmail.reebrando.myinbox.Adapter

/**
 * Created by Rodrigo on 15/05/2018.
 */
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import com.gmail.reebrando.myinbox.ItemRowListener
import com.gmail.reebrando.myinbox.Model.Mensagem
import com.gmail.reebrando.myinbox.R
import java.util.*

class ListItemsAdapter(context: Context, messageItemList: MutableList<Mensagem>) : BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList = messageItemList
    private var rowListener: ItemRowListener = context as ItemRowListener

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val novoCorreio: String? = itemList.get(position).novoCorreio
//        val dataChegada: String? = itemList.get(position).dataChegada
//        val retirada: Boolean = itemList.get(position).retirada
//        val dataRetirada: String? = itemList.get(position).dataRetirada

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

        vh.novoCorreio.text = novoCorreio
//        vh.retirada.isChecked = retirada
//        vh.dataChegada.text = dataChegada
//        vh.dataRetirada.text = dataRetirada

//        vh.retirada.setOnClickListener {
//            rowListener.modifyItemState(id, !done) }
//        vh.ibExclusao.setOnClickListener {
//            rowListener.onItemDelete(objectId) }

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

        val novoCorreio: TextView = row!!.findViewById<TextView>(R.id.tvNovoCorreio) as TextView
//        val dataChegada: TextView = row!!.findViewById<TextView>(R.id.tvDataChegada) as TextView
//        val retirada: CheckBox = row!!.findViewById<CheckBox>(R.id.cbRetirada) as CheckBox
//        val dataRetirada: TextView = row!!.findViewById<TextView>(R.id.tvDataChegada) as TextView
//        val ibExclusao: ImageButton = row!!.findViewById<ImageButton>(R.id.ivExcluir) as ImageButton
    }
}