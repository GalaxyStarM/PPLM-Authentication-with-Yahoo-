package id.ac.unri.submission_one

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import id.ac.unri.submission_one.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(){

    private lateinit var firebaseAuth: FirebaseAuth
    private var binding: ActivityLoginBinding? = null

    companion object{
        private var TAG = "Login Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()

        binding?.btnRegister?.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding?.btnForgotPass?.setOnClickListener {
            val intent = Intent(this, ForgotPassActivity::class.java)
            startActivity(intent)
        }

        binding?.btnLogin?.setOnClickListener {
            val email = binding?.etEmail?.text?.toString()
            val pass = binding?.etPassword?.text?.toString()

            if(email!!.isNotEmpty() && pass!!.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    setLoadingState(true)
                    if (it.isSuccessful){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        saveLoginStatus(true)
                        finish()
                    }else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                        Log.e(TAG, it.exception.toString())
                    }
                }
            }else{
                Toast.makeText(this, getString(R.string.empty_field), Toast.LENGTH_LONG).show()
            }
        }

        binding?.btnYahoo?.setOnClickListener{
            val provider = OAuthProvider.newBuilder("yahoo.com")
                .addCustomParameter("prompt", "login")
                .addCustomParameter("language", "id")

            firebaseAuth.startActivityForSignInWithProvider(this, provider.build())
                .addOnSuccessListener { authResult ->
                    val user = authResult.user
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    saveLoginStatus(true)
                    finish()
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "Yahoo sign-in failed", exception)
                }

        }
    }

    private fun setLoadingState(loading: Boolean) {
        when(loading) {
            true -> {
                binding?.btnLogin?.visibility = View.INVISIBLE
                binding?.progressBar?.visibility = View.VISIBLE
            }
            false -> {
                binding?.btnLogin?.visibility = View.VISIBLE
                binding?.progressBar?.visibility = View.INVISIBLE
            }
        }
    }

    private fun saveLoginStatus(isLoggedIn: Boolean) {
        val sharedPreferences = getSharedPreferences("login_status", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}