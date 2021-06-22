package com.mgr.trazabilizator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerEmail: EditText
    private lateinit var registerName: EditText
    private lateinit var registerPassword: EditText
    private lateinit var registerPassword2: EditText
    private lateinit var registernowButton: Button
    private lateinit var goToLoginButton: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth
        registerEmail = findViewById(R.id.registerEmail)
        registerName = findViewById(R.id.registerName)
        registerPassword = findViewById(R.id.registerPassword)
        registerPassword2 = findViewById(R.id.registerPassword2)
        registernowButton = findViewById(R.id.registernowButton)
        goToLoginButton = findViewById(R.id.goToLoginButton)
        registernowButton.setOnClickListener {
            val email = registerEmail.text.toString()
            val name = registerName.text.toString()
            val password = registerPassword.text.toString()
            val password2 = registerPassword2.text.toString()
            if (password.equals(password2) && checkEmpty(email, name, password, password2)) {
                if ("@iesamericocastro.com" in email) {
                    register(email, password, name)
                } else {
                    Toast.makeText(applicationContext, R.string.domainFailed, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
        goToLoginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }

    private fun register(email: String, password: String, name: String) {
        val db = Firebase.firestore
        val user = hashMapOf(
            "Name" to name,
            "Email" to email.toLowerCase()
        )
        db.collection("users")
            .add(user)

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    Toast.makeText(applicationContext, R.string.registerComplete, Toast.LENGTH_LONG)
                        .show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, R.string.registerFailed, Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

    private fun checkEmpty(
        email: String,
        name: String,
        password: String,
        password2: String,
    ): Boolean {
        return email.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty() && password2.isNotEmpty()
    }
}