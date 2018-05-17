package com.gmail.reebrando.myinbox

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
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
import com.google.firebase.database.DataSnapshot



class ListInboxActivity : AppCompatActivity(), ItemRowListener {

    lateinit var mDatabase: DatabaseReference
    lateinit var adapter: ListItemsAdapter
    var messageItemList: MutableList<Mensagem>? = null
    var listViewItems: ListView? = null

    private val TAG = "ListInboxActivity"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_inbox)

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        listViewItems = findViewById<View>(R.id.items_list) as ListView

        //Adding click listener for FAB
        fab.setOnClickListener { _ ->

            //Show Dialog here to add new Item
            addNewItemDialog()
        }

        mDatabase = FirebaseDatabase.getInstance().reference
        messageItemList = mutableListOf<Mensagem>()
        adapter = ListItemsAdapter(this, messageItemList!!)
        listViewItems!!.setAdapter(adapter)
        mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)
    }

    private var itemListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {

            for (snapshot in dataSnapshot.children) {
                if (dataSnapshot.hasChild("message")) {
                    val mensagem = snapshot.getValue<Mensagem>(Mensagem::class.java)
                    System.out.println(mensagem!!.novoCorreio)
                    Log.w(TAG, "novo correio : " + mensagem!!.novoCorreio)
                }

            }

//            var message = dataSnapshot.getValue(Mensagem::class.java)
//
//            if (dataSnapshot.hasChild("message")) {
//
//                message = dataSnapshot.getValue(Mensagem::class.java)
//
//                // Get Post object and use the values to update the UI
//                addDataToList(dataSnapshot)
        }
        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Item failed, log a message
            Log.w(TAG, "loadItem:onCancelled", databaseError.toException())
        }
    }

    private fun addDataToList(dataSnapshot: DataSnapshot) {
        dataSnapshot.children.forEach {
            Log.w(TAG, "it.key " + it.key.toString())

            if (it.key.equals("message")) {

                val items = it.children.iterator()

                // Check if current database contains any collection
                if (items.hasNext()) {

                    val messageListindex = items.next()
                    val itemsIterator = messageListindex.children.iterator()

                    while (itemsIterator.hasNext()) {

                        Log.w(TAG, "itemsIterator: " + itemsIterator.toString())

                        // get current item
                        val currentItem = itemsIterator.next()

                        Log.w(TAG, "currentItem: " + currentItem.toString())

                        val map = it.getValue() as HashMap<String, Any?>

                        Log.w(TAG, "map: " + map.toString())

                        val msgItem = Mensagem.create()

                        // get current data in a map


                        // key will return Firebase ID
                        //msgItem.id = currentItem.key
                        //msgItem.retirada = map.get("retirada") as Boolean
                        //msgItem.dataChegada = map.get("dataChegada") as String?
        //                if (map.containsKey("dataRetirada")) {
        //                    msgItem.dataRetirada = map.get("dataRetirada") as Date?
        //                }

                        msgItem.id = currentItem.key
        //                msgItem.dataChegada = map.get("dataChegada") as String
                        msgItem.novoCorreio = map.get("novoCorreio") as String
                        messageItemList!!.add(msgItem)
                    }
                }

                // alert adapter that has changed
                adapter.notifyDataSetChanged()


                Log.w(TAG, "onDataChange: " + it.getValue().toString())

                val item : Mensagem = it.getValue<Mensagem>(Mensagem::class.java)!!
                val map = it.getValue() as HashMap<String, Any?>

                Log.w(TAG, "item " + item)

                Log.w(TAG, "map " + map)
//                val map = it.getValue() as HashMap<String, Any>
//                val msgItem = Mensagem.create()
//
//                msgItem.id = item.id // map.get("id") as String
//                msgItem.novoCorreio = item.novoCorreio// map.get("novoCorreio") as String
//
//                messageItemList!!.add(msgItem)
//
//                adapter.notifyDataSetChanged()

            }
            else {
                Log.w(TAG, "não achou message")
            }
        }
//
//        val map = dataSnapshot.child("message").getValue() as HashMap<String, Any?>
//
//        val msgItem = Mensagem.create()
//
//        //msgItem.id
//        msgItem.novoCorreio = map.get("novoCorreio") as String
//        messageItemList!!.add(msgItem)
//
//        adapter.notifyDataSetChanged()


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
