
package id.ac.unri.submission_one

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import id.ac.unri.submission_one.databinding.ActivityForgotPassBinding

class ForgotPassActivity : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth
    private var binding: ActivityForgotPassBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPassBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        binding?.fbForgotPass?.setOnClickListener{
            val email = binding?.etEmail?.text.toString()
            if (TextUtils.isEmpty(email)){
                Toast.makeText(this, getString(R.string.email_error), Toast.LENGTH_LONG).show()
            }else{
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this, getString(R.string.reset_password), Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }

        }

    }
}