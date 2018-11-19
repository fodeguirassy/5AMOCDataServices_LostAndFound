package projetannuel.bigteam.com.lostandfound

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.google.firebase.auth.FirebaseAuth
import jp.wasabeef.blurry.Blurry
import projetannuel.bigteam.com.lostandfound.R.layout.activity_main
import projetannuel.bigteam.com.lostandfound.navigation.AppNavigator

class MainActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()
    private val appNavigator : AppNavigator by injector.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
        setContentView(R.layout.activity_main)

        if(FirebaseAuth.getInstance().currentUser != null) {
            appNavigator.displayDashboard(FirebaseAuth.getInstance().currentUser!!.uid)
        } else {
            appNavigator.displayRegister()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.fragments.forEach {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.v("@@nav", "onSupportNavigateUp")
        if (fragmentManager.backStackEntryCount >= 0) {
            fragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        Log.v("@@nav", "onBackPressed")
        if (fragmentManager.backStackEntryCount >= 0) {
            fragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

}
