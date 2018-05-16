package com.gmail.reebrando.myinbox

/**
 * Created by Rodrigo on 16/05/2018.
 */
interface ItemRowListener {

    fun modifyItemState(itemObjectId: String, isDone: Boolean)

    fun onItemDelete(itemObjectId: String)

}