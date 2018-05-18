package com.gmail.reebrando.myinbox

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ListView
import com.gmail.reebrando.myinbox.adapters.InboxAdapter
import com.gmail.reebrando.myinbox.config.Constants
import com.gmail.reebrando.myinbox.models.Inbox
import com.google.firebase.database.*
import org.jetbrains.anko.toast
import java.util.HashMap

class InboxActivity : AppCompatActivity(), ItemRowListener {

    lateinit var mDatabase: DatabaseReference
    var toDoItemList: MutableList<Inbox>? = mutableListOf()
    lateinit var adapter: InboxAdapter
    private var listViewItems: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)

        listViewItems = findViewById(R.id.items_list) as ListView

        mDatabase = FirebaseDatabase.getInstance().reference
        adapter = InboxAdapter(this, toDoItemList!!)
        listViewItems!!.adapter = adapter
        mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton

        //Adding click listener for FAB
        fab.setOnClickListener { view ->
            //Show Dialog here to add new Item
            addNewItemDialog()
        }
    }

    override fun modifyItemState(itemObjectId: String, isDone: Boolean) {
        val itemReference = mDatabase.child(Constants.FIREBASE_ITEM).child(itemObjectId)
        itemReference.child("done").setValue(isDone);
    }

    override fun onItemDelete(itemObjectId: String) {
        //get child reference in database via the ObjectID
        val itemReference = mDatabase.child(Constants.FIREBASE_ITEM).child(itemObjectId)
        //deletion can be done via removeValue() method
        itemReference.removeValue()
    }

    var itemListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            addDataToList(dataSnapshot)
        }
        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Item failed, log a message
            Log.w("MainActivity", "loadItem:onCancelled", databaseError.toException())
        }
    }

    private fun addDataToList(dataSnapshot: DataSnapshot) {
        val items = dataSnapshot.children.iterator()
        //Check if current database contains any collection
        if (items.hasNext()) {
            val toDoListindex = items.next()
            val itemsIterator = toDoListindex.children.iterator()

            //check if the collection has any to do items or not
            while (itemsIterator.hasNext()) {
                //get current item
                val currentItem = itemsIterator.next()
                val todoItem = Inbox.create()
                //get current data in a map
                val map = currentItem.getValue() as HashMap<String, Any>
                //key will return Firebase ID
                todoItem.objectId = currentItem.key
                todoItem.done = map.get("done") as Boolean?
                todoItem.itemText = map.get("itemText") as String?
                toDoItemList!!.add(todoItem)
            }
        }
        //alert adapter that has changed
        adapter.notifyDataSetChanged()
    }

    private fun addNewItemDialog() {
        val alert = AlertDialog.Builder(this)
        val itemEditText = EditText(this)
        alert.setMessage("Add New Item")
        alert.setTitle("Enter To Do Item Text")
        alert.setView(itemEditText)
        alert.setPositiveButton("Submit") { dialog, positiveButton ->
            val todoItem = Inbox.create()
            todoItem.itemText = itemEditText.text.toString()
            todoItem.done = false
            //We first make a push so that a new item is made with a unique ID
            val newItem = mDatabase.child(Constants.FIREBASE_ITEM).push()
            todoItem.objectId = newItem.key
            //then, we used the reference to set the value on that ID
            newItem.setValue(todoItem)
            dialog.dismiss()
            toast("Item saved with ID " + todoItem.objectId)

        }
        alert.show()
    }

}
