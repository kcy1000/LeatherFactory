package com.novaq.leatherfactory

import android.app.Dialog
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.novaq.leatherfactory.databinding.MainActivityBinding
import com.novaq.leatherfactory.log.LogManager
import com.novaq.leatherfactory.ui.login.SignInIntentContract

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var progressDialog: Dialog

    //google login
    private var googleSignInClient: GoogleSignInClient? = null
    private lateinit var auth: FirebaseAuth

    //MainActivity -> 구글로그인 화면 -> MainActivity 로 돌아온 후 콜백 함수.
    //구글로그인 화면을 통해 얻어온 tokenId를 이용해 Firebase 사용자 인증 정보로 교환하고
    //해당 정보를 사용해 Firebase에 인증합니다.
    private val launcher = registerForActivityResult(SignInIntentContract()) { result: String? ->
        result?.let {
            firebaseAuthWithGoogle(it)  //tokenId를 이용해 firebase에 인증하는 함수 호출.
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        createProgressDialog()

        auth = Firebase.auth

    }

    override fun onStart() {
        super.onStart()

    }

    private fun createProgressDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(R.layout.progress)
        progressDialog = builder.create()
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    fun isNetworkConnected(): Boolean {
        val cm = applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun hideLoading() {
        if (::progressDialog.isInitialized) {
            if (progressDialog.isShowing) {
                progressDialog.dismiss()
            }
        } else {
            createProgressDialog()
        }
    }

    fun showLoading() {
        if (::progressDialog.isInitialized) {
            if (!progressDialog.isShowing) {
                progressDialog.show()
            }
        } else {
            createProgressDialog()
            progressDialog.show()
        }
    }

    fun hideKeyboard() {
        if(currentFocus is TextInputEditText) return

        currentFocus?.let {
            val inputMethodManager = ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    fun loginGoogle() {
        //Launcher를 실행해 LoginActivity -> 구글 로그인 화면으로 이동.
        launcher.launch(getString(R.string.default_web_client_id))
    }

    //tokenId를 이용해 firebase에 인증하는 함수.
    private fun firebaseAuthWithGoogle(idToken: String) {
        //it가 tokenId, credential은 Firebase 사용자 인증 정보.
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        //Firebase 사용자 인증 정보(credential)를 사용해 Firebase에 인증.
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    // moveMainHome()
                    LogManager.log.d("[성공] firebaseAuthWithGoogle")
                } else {
                    LogManager.log.e("[에러] firebaseAuthWithGoogle => ${task.exception}")
                }
            }
    }
}