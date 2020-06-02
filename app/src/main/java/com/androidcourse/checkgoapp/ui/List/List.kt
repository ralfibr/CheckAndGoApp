package com.androidcourse.checkgoapp.ui.List
import android.app.ListActivity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidcourse.checkgoapp.R
import com.androidcourse.checkgoapp.adapter.ItemAdapter
import com.androidcourse.checkgoapp.model.Item
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.androidcourse.checkgoapp.ui.Chat.Chat
import com.androidcourse.checkgoapp.ui.Profile
import kotlinx.android.synthetic.main.item_layout.*


class List : AppCompatActivity() {

    private var auth: FirebaseAuth? = null
    private val checkList = arrayListOf<Item>()
    private val itemAdapter = ItemAdapter(checkList)
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private var inputItem: EditText? = null
    private lateinit var database: DatabaseReference
    private val viewModel: ListActivityModel by viewModels()
    private var checkbox1: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        database = Firebase.database.reference
        supportActionBar?.title = "Your checklist"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_info)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        checkbox1 = findViewById(R.id.checkBox)
        observeViewModel()
        initViews()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("/Items")
        inputItem = findViewById(R.id.itemEdit)
        deleteBtn.setOnClickListener(View.OnClickListener {


            val dialogBuilder = AlertDialog.Builder(this)

            // set message of alert dialog
            dialogBuilder.setMessage("Are yo sure to delete your checklist ?")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->
                    deleteCheckList()
                    finish()
                    navigateBack()
                })
                // negative button text and action
                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Delete all items")
            // show alert dialog
            alert.show()})
        val navigationView = findViewById(R.id.bottom_nav) as BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener() { item ->
            when (item.itemId) {
                R.id.navigation_list -> {
                }
                R.id.navigation_chat ->
                    navigateToChat()
                R.id.navigation_profile -> {
                    navigateToProfile()
                }
            }
            true

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.drawable.ic_action_delete -> {
                deleteCheckList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeViewModel() {
        viewModel.items.observe(this, Observer() { items ->
            this@List.checkList.clear()
            this@List.checkList.addAll(items)
            itemAdapter.notifyDataSetChanged()
        })

    }
//    fun getDataFromFireBase(){
//        database.addListenerForSingleValueEvent(object: ValueEventListener{
//            override fun onCancelled(p0: DatabaseError) {
//
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                p0.children.forEach {
////                    val item = it.getValue(ItemFirebase::class.java)
////
////                    if (item != null) {
////                       // tvItem?.text = item?.item
////                        Log.d("test", item.item)
////                    }
////                    return
//                }
//            }
//
//        })
//
//    }

    private fun initViews() {
        rvItems.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvItems.adapter = itemAdapter
        rvItems.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvItems)
        floatingActionButton.setOnClickListener { addItem() }
    }

    fun navigateToProfile() {
        startActivity(Intent(this, Profile::class.java))
    }
    fun navigateBack() {
        startActivity(Intent(this, List::class.java))
    }
    fun navigateToChat() {
        startActivity(Intent(this, Chat::class.java))
    }
    private fun addItem() {
        val itemName = inputItem!!.text.toString().trim()
        // hint.setVisibility(View.VISIBLE)
        if (itemName.isEmpty()) {
            Toast.makeText(
                this, "Item cannot be empty",
                Toast.LENGTH_LONG
            ).show()

            return

        }

        mainScope.launch {
            Log.d("MainActivity", itemName)
            val item = Item(name = itemName)
            withContext(Dispatchers.IO) {
                viewModel.insertItem(item)
            }
            writeNewItemToFirebase(inputItem!!.text.toString().trim())
            inputItem!!.setText("")
        }

    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                itemAdapter.notifyItemChanged(viewHolder.adapterPosition)

                if (direction == ItemTouchHelper.RIGHT) {
                    Snackbar.make(rvItems, "Item is on ready state", Snackbar.LENGTH_SHORT).show()

                } else {
                    val position = viewHolder.adapterPosition
                    val itemToDelete = checkList[position]

                            database.child(auth?.currentUser?.uid.toString())
                                .child(itemToDelete.name).removeValue()
                            viewModel.deleteItem(itemToDelete)


                        Snackbar.make(
                            rvItems
                            , // Parent view
                            itemToDelete.name + " is ready and is deleted ", // Message to show
                            Snackbar.LENGTH_SHORT // How long to display the message.
                        ).show()

                }
                return
            }
        }
        return ItemTouchHelper(callback)
    }

    //
//    private fun getCheckListFromDatabase() {
//        mainScope.launch {
//            // call product repository
//            val chcekList = withContext(Dispatchers.IO) {
//                itemRepository.getAllItems()
//            }
//            checkList.clear()
//            checkList.addAll(chcekList)
//            itemAdapter.notifyDataSetChanged()
//        }
//    }
    private fun deleteCheckList() {
        database.child(auth?.currentUser?.uid.toString() + "/").removeValue()
        viewModel.deleteAll()
        Snackbar.make(
            rvItems
            , // Parent view
            "All items is deleted", // Message to show
            Snackbar.LENGTH_SHORT // How long to display the message.
        ).show()



    }

    private fun writeNewItemToFirebase(name: String) {
        database.child(auth?.currentUser?.uid.toString() + "/" + name).setValue(name)

    }


}
