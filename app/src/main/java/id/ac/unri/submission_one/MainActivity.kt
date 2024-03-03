package id.ac.unri.submission_one

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import id.ac.unri.submission_one.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding?.btnLogout?.setOnClickListener {
            firebaseAuth.signOut()
            clearLoginStatus()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
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