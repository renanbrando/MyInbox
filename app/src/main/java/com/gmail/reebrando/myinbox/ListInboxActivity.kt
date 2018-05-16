package com.gmail.reebrando.myinbox

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ListView
import com.gmail.reebrando.myinbox.Adapter.ListItemsAdapter
import com.gmail.reebrando.myinbox.Model.Mensagem
import com.google.firebase.database.*
import java.util.*

class ListInboxActivity : AppCompatActivity(), ItemRowListener {

    lateinit var mDatabase: DatabaseReference
    lateinit var adapter: ListItemsAdapter
    var messageItemList: MutableList<Mensagem>? = null
    var listViewItems: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_inbox)

        mDatabase = FirebaseDatabase.getInstance().reference

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        listViewItems = findViewById<View>(R.id.items_list) as ListView

        //Adding click listener for FAB
        fab.setOnClickListener { view ->

            //Show Dialog here to add new Item
            addNewItemDialog()
        }

        messageItemList = mutableListOf<Mensagem>()

        adapter = ListItemsAdapter(this, messageItemList!!)
        listViewItems!!.setAdapter(adapter)

        mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)

    }

    private var itemListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            addDataToList(dataSnapshot)
        }
        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Item failed, log a message
            Log.w("ListInboxActivity", "loadItem:onCancelled", databaseError.toException())
        }
    }

    private fun addDataToList(dataSnapshot: DataSnapshot) {

        val items = dataSnapshot.children.iterator()

        // Check if current database contains any collection
        if (items.hasNext()) {

            val messageListindex = items.next()
            val itemsIterator = messageListindex.children.iterator()

            // check if the collection has any to do items or not
            while (itemsIterator.hasNext()) {

                // get current item
                val currentItem = itemsIterator.next()
                val msgItem = Mensagem.create()

                // get current data in a map
                val map = currentItem.getValue() as HashMap<String, Any>

                // key will return Firebase ID
                //msgItem.id = currentItem.key
                //msgItem.retirada = map.get("retirada") as Boolean
                //msgItem.dataChegada = map.get("dataChegada") as String?
//                if (map.containsKey("dataRetirada")) {
//                    msgItem.dataRetirada = map.get("dataRetirada") as Date?
//                }

                msgItem.dataChegada = currentItem.key
                messageItemList!!.add(msgItem)
            }
        }

        // alert adapter that has changed
        adapter.notifyDataSetChanged()
    }

    private fun addNewItemDialog() {
//        val alert = AlertDialog.Builder(this)
//        val itemEditText = EditText(this)
//        alert.setMessage("Add New Item")
//        alert.setTitle("Enter To Do Item Text")
//        alert.setView(itemEditText)
//        alert.setPositiveButton("Submit") { dialog, positiveButton ->   }
//        alert.show()
    }

    override fun modifyItemState(itemObjectId: String, isDone: Boolean) {

        val itemReference = mDatabase.child("mensagem").child(itemObjectId)

        itemReference.child("retirada").setValue(isDone);

    }

    //delete an item
    override fun onItemDelete(itemObjectId: String) {

        //get child reference in database via the ObjectID
        val itemReference = mDatabase.child("mensagem").child(itemObjectId)

        //deletion can be done via removeValue() method
        itemReference.removeValue()
    }
}
