package id.ac.unri.submission_one

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.ac.unri.submission_one.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var channelList: ArrayList<Channel>
    private lateinit var recyclerView: RecyclerView

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        channelList = ArrayList()
        recyclerView = binding?.rvChannel!!
        recyclerView.layoutManager = LinearLayoutManager(this)

        val databaseRef = database.getReference("channel")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                channelList.clear()
                for (channelSnapshot in snapshot.children) {
                    val channel = channelSnapshot.getValue(Channel::class.java)
                    if (channel != null && channel.status == true) {
                        channelList.add(channel)
                    }
                }
                recyclerView.adapter = ChannelAdapter(channelList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_logout -> {
                firebaseAuth.signOut()
                clearLoginStatus()
                val intent = Intent (this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun clearLoginStatus() {
        val sharedPreferences = getSharedPreferences("login_status", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("isLoggedIn") // Hapus status login
        editor.apply()
    }
}