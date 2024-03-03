package id.ac.unri.submission_one

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import id.ac.unri.submission_one.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions
    private lateinit var firebaseAuth: FirebaseAuth
    private var binding: ActivityRegisterBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        binding?.btnLogin?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding?.btnRegister?.setOnClickListener {
            val username = binding?.etUsername?.text?.toString()
            val email = binding?.etEmail?.text?.toString()
            val pass = binding?.etPassword?.text?.toString()
            val confirmPass = binding?.etConfirmPassword?.text?.toString()

            if(username!!.isNotEmpty() && email!!.isNotEmpty() && pass!!.isNotEmpty() && confirmPass!!.isNotEmpty()) {
                if(pass == confirmPass) {
                    setLoadingState(true)
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this, getString(R.string.success_signup), Toast.LENGTH_LONG).show()
                            finish()
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                }else{
                    setLoadingState(false)
                    Toast.makeText(this, getString(R.string.confirm_pass_check), Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, getString(R.string.empty_field), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setLoadingState(loading: Boolean) {
        when(loading) {
            true -> {
                binding?.btnRegister?.visibility = View.INVISIBLE
                binding?.progressBar?.visibility = View.VISIBLE
            }
            false -> {
                binding?.btnRegister?.visibility = View.VISIBLE
                binding?.progressBar?.visibility = View.INVISIBLE
            }
        }
    }
}