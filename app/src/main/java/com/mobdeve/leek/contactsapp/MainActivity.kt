package com.mobdeve.leek.contactsapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.leek.contactsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MAIN_ACTIVITY"
    }

    private lateinit var sp: SharedPreferences
    private lateinit var contactsSet: MutableSet<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        sp = getSharedPreferences("MyContacts", Context.MODE_PRIVATE)

        contactsSet = sp.getStringSet("contacts", HashSet())?.toMutableSet()!!
        loadContacts(viewBinding)

        viewBinding.addBtn.setOnClickListener(View.OnClickListener {
            val name = viewBinding.nameEtv.text.toString()
            val number = viewBinding.numberEtv.text.toString()

            if (name != "" || number != "") {
                Log.d(TAG, "onClick: added properly")
                val temp = "${viewBinding.contactsStringHolderTv.text}$name : $number\n"
                viewBinding.contactsStringHolderTv.setText(temp)

                viewBinding.nameEtv.setText("")
                viewBinding.numberEtv.setText("")

                val new = "$name: $number"
                contactsSet.add(new)
                saveContacts()
            } else {
                Log.d(TAG, "onClick: not added properly")
            }
        })
    }

    private fun loadContacts(viewBinding : ActivityMainBinding) {
        for (contact in contactsSet) {
            val updatedContactText = viewBinding.contactsStringHolderTv.text.toString() + contact + "\n"
            viewBinding.contactsStringHolderTv.text = updatedContactText
        }
    }

    private fun saveContacts() {
        sp.edit().putStringSet("contacts", contactsSet).apply()
    }
}