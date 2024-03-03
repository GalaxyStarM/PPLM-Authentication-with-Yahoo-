package id.ac.unri.submission_one

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.ac.unri.submission_one.databinding.ActivitySplashScreenBinding

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    private var binding: ActivitySplashScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()

        if (isLoggedIn()) {
            binding?.imgSs?.alpha = 0f
            binding?.imgSs?.animate()?.setDuration(5000)?.alpha(1f)?.withEndAction{
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }else{
            binding?.imgSs?.alpha = 0f
            binding?.imgSs?.animate()?.setDuration(5000)?.alpha(1f)?.withEndAction {
                val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("login_status", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }
}