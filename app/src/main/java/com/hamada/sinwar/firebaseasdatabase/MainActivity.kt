@file:Suppress("DEPRECATION")

package com.hamada.sinwar.firebaseasdatabase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), Adapter.OnClickItem {

    private val data = mutableListOf<Model>()
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Firebase.firestore
        val recycler = findViewById<RecyclerView>(R.id.mainRV)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val noData = findViewById<TextView>(R.id.noData)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.show()

        db.collection("contacts").get().addOnSuccessListener {
            for (doc in it.documents){
                data.add(Model(doc.id, doc.getString("name")!! , doc.getString("number")!!.toInt(), doc.getString("address")!!))
            }
            val adapter = Adapter(this, data, this)
            recycler.layoutManager = LinearLayoutManager(this)
            recycler.adapter = adapter
            progressDialog.dismiss()
            if (data.isEmpty()){
                noData.visibility = View.VISIBLE
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }

        fab.setOnClickListener {
            startActivity(Intent(this, AddContactActivity::class.java))
        }
    }

    override fun onClick(position: Int) {
        Toast.makeText(this, "${data[position].name} ${data[position].number} ${data[position].address}", Toast.LENGTH_SHORT).show()
    }
}