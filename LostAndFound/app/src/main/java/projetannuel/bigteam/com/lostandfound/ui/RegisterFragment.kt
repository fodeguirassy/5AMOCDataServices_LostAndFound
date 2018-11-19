package projetannuel.bigteam.com.lostandfound.ui


import android.app.Activity.RESULT_OK
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.firebase.ui.auth.AuthUI
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.FragmentInjector
import com.github.salomonbrys.kodein.instance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import projetannuel.bigteam.com.lostandfound.R
import projetannuel.bigteam.com.lostandfound.navigation.AppNavigator
import projetannuel.bigteam.com.lostandfound.network.CloudFunctionsService
import projetannuel.bigteam.com.lostandfound.network.LocalApi
import projetannuel.bigteam.com.lostandfound.network.model.CloudSqlUser


class RegisterFragment : Fragment(), FragmentInjector {

    override val injector = KodeinInjector()

    private val localApi : LocalApi by injector.instance()

    private val appNavigator :AppNavigator by injector.instance()

    private val disposableBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()

        val providers = listOf(
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.PhoneBuilder().build()
        )
        val intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(providers)
                .setTheme(R.style.AppTheme)
                .setLogo(R.drawable.ic_logo_web)
                .build()

        startActivityForResult(
                intent, RegisterFragment.REGISTER_REQUEST_CODE
        )
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view?.setBackgroundColor(ContextCompat.getColor(context, R.color.black_transparent))
    }

    private fun firebaseUserToCloudSql(firebaseUser: FirebaseUser): CloudSqlUser {

        return CloudSqlUser(userId = firebaseUser.uid,
                email = firebaseUser.email!!,
                displayName = firebaseUser.displayName!!,
                photoUrl = "${firebaseUser.photoUrl!!}"
        )

    }

    private fun saveUser(cloudSqlUser: CloudSqlUser) {

        val saveUserDisposable = localApi.saveCloudSqlUser(cloudSqlUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    Log.v("@@succes", "$it")
                    appNavigator.displayDashboard(cloudSqlUser.userId)

                }, {
                            Log.v("@error", "${it.printStackTrace()}")
                })

        disposableBag.add(saveUserDisposable)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            RESULT_OK -> {
                if (requestCode == RegisterFragment.REGISTER_REQUEST_CODE) {
                    FirebaseAuth.getInstance().currentUser?.let {
                        Log.v("@@auth", "$it.")

                        saveUser(firebaseUserToCloudSql(it))
                    }
                }
            }
        }
    }

    override fun onStop() {
        disposableBag.clear()
        super.onStop()
    }


    companion object {
        const val REGISTER_REQUEST_CODE = 4444
        const val FRAGMENT_TAG = "Register"
    }


}
