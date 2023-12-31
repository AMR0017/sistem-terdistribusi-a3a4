package com.example.sistem_terdistribusi_a3a4.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import com.example.sistem_terdistribusi_a3a4.databinding.ActivityLoginBinding
import com.example.sistem_terdistribusi_a3a4.ui.homepage.HomepageActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser != null){
            val intent = Intent(this, HomepageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.loginEdt.text.toString()
            val password = binding.passwordEdt.text.toString()

            binding.progressBar.visibility = ProgressBar.VISIBLE
            if (email.isNotEmpty() && password.isNotEmpty()){
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener (this){task ->
                        if (task.isSuccessful){
                            binding.progressBar.visibility = ProgressBar.GONE
                            val intent = Intent(this, HomepageActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this, "Login failed. Please check your credentials", Toast.LENGTH_SHORT).show()
                            binding.progressBar.visibility = ProgressBar.GONE
                        }
                    }
            }else{
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = ProgressBar.GONE
            }
        }

        binding.forgotPassword.setOnClickListener {
            val email = binding.loginEdt.text.toString()
            binding.progressBar.visibility = ProgressBar.VISIBLE
            if (email.isNotEmpty()){
                mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task->
                        if (task.isSuccessful){
                            Toast.makeText(this, "Password reset email sent. Please check your email.", Toast.LENGTH_LONG).show()
                            binding.progressBar.visibility = ProgressBar.GONE
                        }else{
                            Toast.makeText(this, "Password reset email could not be sent. Please try again later.", Toast.LENGTH_LONG).show()
                            binding.progressBar.visibility = ProgressBar.GONE
                        }
                    }
            } else{
                Toast.makeText(this, "Please enter your email address to reset your password", Toast.LENGTH_LONG).show()
                binding.progressBar.visibility = ProgressBar.GONE
            }
        }
    }
}