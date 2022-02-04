package com.example.onlineshop.view.login

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.onlineshop.checkinternet.NetworkChangeListener
import com.example.onlineshop.databinding.ActivityLoginBinding
import com.example.onlineshop.utils.MaskWatcher
import com.example.onlineshop.view.MainActivity
import com.example.onlineshop.utils.PrefUtils
import com.example.onlineshop.viewmodel.MainViewModel

enum class LoginState {
    CHECK_PHONE,
    LOGIN,
    REGISTRATION,
    CONFIRM,
}

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: MainViewModel
    private var broadcastReceiver: BroadcastReceiver? = null

    var state = LoginState.CHECK_PHONE
    var phone = ""

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        broadcastReceiver = NetworkChangeListener(this, this)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()

        })

        viewModel.progress.observe(this, Observer {
            binding.flProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.checkPhoneData.observe(this, Observer {
            if (it.is_reg) {
                state = LoginState.LOGIN
            } else {
                state = LoginState.REGISTRATION
            }
            initViews()
        })

        viewModel.loginData.observe(this, Observer {
            PrefUtils.setToken(it.token)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        viewModel.confirmData.observe(this, Observer {
            PrefUtils.setToken(it.token)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        viewModel.registrationData.observe(this, Observer {
            state = LoginState.CONFIRM
            initViews()
        })


        binding.btnNext.setOnClickListener {
            when (state) {
                LoginState.CHECK_PHONE -> {
                    phone = binding.edPhone.text.toString().replace(" ", "")
                    viewModel.checkPhone(phone)
                    PrefUtils.setNumber(phone)
                }
                LoginState.LOGIN -> {
                    viewModel.login(phone, binding.edPassword.text.toString())
                }
                LoginState.REGISTRATION -> {
                    val fullname = binding.edFullname.text.toString()
                    val password = binding.edPassword.text.toString()
                    val repassword = binding.edRePassword.text.toString()
                    if (fullname.isNullOrEmpty()) {
                        Toast.makeText(this, "Please input full name!", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    if (password.isNullOrEmpty()) {
                        Toast.makeText(this, "Please input password!", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    if (password != repassword) {
                        Toast.makeText(this, "Please input correct password!", Toast.LENGTH_LONG)
                            .show()
                        return@setOnClickListener
                    }
                    viewModel.registrationData(fullname, phone, password)
                    PrefUtils.setFullName(fullname)

                }
                LoginState.CONFIRM -> {
                    viewModel.confirmUser(phone, binding.edCode.text.toString())
                }
            }
        }
        initViews()

    }

    fun initViews() {
        binding.lyFullname.visibility = View.GONE
        binding.lySMSCode.visibility = View.GONE
        binding.lyPassword.visibility = View.GONE
        binding.lyRePassword.visibility = View.GONE

        when (state) {
            LoginState.CHECK_PHONE -> {
                binding.tvTitleLogin.text = "Login"
                binding.edPhone.isEnabled = true
            }
            LoginState.LOGIN -> {
                binding.tvTitleLogin.text = "Login"
                binding.lyPassword.visibility = View.VISIBLE
                binding.edPhone.isEnabled = false
            }
            LoginState.REGISTRATION -> {
                binding.tvTitleLogin.text = "Registration"
                binding.lyFullname.visibility = View.VISIBLE
                binding.lyPassword.visibility = View.VISIBLE
                binding.lyRePassword.visibility = View.VISIBLE
                binding.edPhone.isEnabled = false
            }
            LoginState.CONFIRM -> {
                binding.tvTitleLogin.text = "Registration"
                binding.lySMSCode.visibility = View.VISIBLE
                binding.edPhone.isEnabled = false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStop() {
        unregisterReceiver(broadcastReceiver)
        super.onStop()
    }
}