package pt.ulusofona.deisi.a2020.cm.g25.ui.activities.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pt.ulusofona.deisi.a2020.cm.g25.R
import pt.ulusofona.deisi.a2020.cm.g25.domain.interfaces.SplashScreenDataFetcherInterface
import pt.ulusofona.deisi.a2020.cm.g25.ui.activities.MainActivity
import pt.ulusofona.deisi.a2020.cm.g25.ui.viewmodels.splashscreen.SplashScreenViewModel

class SplashScreenActivity : AppCompatActivity(), SplashScreenDataFetcherInterface {

    private var TIME_OUT: Long = 5000 // Para o loader da 1a parte

    private lateinit var viewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {}

        setContentView(R.layout.activity_splashscreen)

        viewModel = ViewModelProviders.of(this).get(SplashScreenViewModel::class.java)
        //loadSplashScreen()
    }

    override fun onStart() {
        super.onStart()
        viewModel.registerListener(this)
        viewModel.loadData()
    }

    private fun loadSplashScreen(){

        Handler(Looper.getMainLooper()).postDelayed({
            // You can declare your desire activity here to open after finishing splash screen. Like MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, TIME_OUT)
    }

    private fun showErrorDialog(error: String) {
        var dialogTitle = ""
        if(error.equals("connection")){
            dialogTitle = getResources().getString(R.string.error_dialog_connection)
        } else {
            dialogTitle = getResources().getString(R.string.error_dialog_timeout)
        }
        MaterialAlertDialogBuilder(this)
            .setTitle(dialogTitle)
            .setMessage(getResources().getString(R.string.error_dialog_description))
            .setNegativeButton(getResources().getString(R.string.error_action_cancel)) { dialog, which ->
                finish()
                System.exit(0)
            }
            .setPositiveButton(getResources().getString(R.string.error_action_retry)) { dialog, which ->
                viewModel.loadData()
            }.show()
    }

    override fun onDataLoadComplete() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onConnectionError() {
        showErrorDialog("connection")
    }

    override fun onTimeoutError() {
        showErrorDialog("timeout")
    }
}