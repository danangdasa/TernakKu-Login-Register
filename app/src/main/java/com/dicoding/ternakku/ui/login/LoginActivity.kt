package com.dicoding.ternakku.ui.login

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.ternakku.MainActivity
import com.dicoding.ternakku.data.retrofit.ApiConfig
import com.dicoding.ternakku.data.retrofit.response.LoginResponse
import com.dicoding.ternakku.databinding.ActivityLoginBinding
import com.dicoding.ternakku.preference.AuthorizeModel
import com.dicoding.ternakku.preference.LoginPreference
import com.dicoding.ternakku.ui.register.RegisterActivity
import com.dicoding.ternakku.viewmodelfactory.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataSetting")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var user: AuthorizeModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        binding.etPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validationPassword()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validationPassword()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.btnLogin.setOnClickListener {
            val emailUser = binding.etEmail.text.toString()
            val passwordUser = binding.etPassword.text.toString()

            userLogin(emailUser, passwordUser)
        }

        binding.btnToRegist.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun validationPassword(){
        val result = binding.etPassword.text
        binding.btnLogin.isEnabled = result != null && result.toString().isNotEmpty() && result.length >= 8
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel(){
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(LoginPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this, {user ->
            this.user = user
        })
    }

    private fun userLogin(email: String, password: String){
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody!= null){
                        val token = responseBody.loginResult.token
                        loginViewModel.saveUserData(AuthorizeModel(token))

                        Toast.makeText(this@LoginActivity, responseBody.message, Toast.LENGTH_SHORT).show()

                        val intentLogin = Intent(this@LoginActivity, MainActivity::class.java)
                        intentLogin.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intentLogin)
                    }
                } else {
                    Toast.makeText(this@LoginActivity, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("ResultActivity", "onFailure: ${t.message.toString()}")
                Toast.makeText(this@LoginActivity, "Gagal instance Retrofit", Toast.LENGTH_SHORT).show()
            }

        })
    }
}