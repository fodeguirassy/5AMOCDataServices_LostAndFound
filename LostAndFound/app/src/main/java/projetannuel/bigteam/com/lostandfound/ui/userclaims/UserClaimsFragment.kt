package projetannuel.bigteam.com.lostandfound.ui.userclaims


import android.os.Bundle
import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.FragmentInjector
import com.github.salomonbrys.kodein.instance
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_lost_and_found.loader_indicator
import kotlinx.android.synthetic.main.fragment_user_claims.user_claims

import projetannuel.bigteam.com.lostandfound.R
import projetannuel.bigteam.com.lostandfound.network.LocalApi

class UserClaimsFragment : Fragment(), FragmentInjector {

    override val injector = KodeinInjector()
    private lateinit var epoxyController: UserClaimsEpoxyController
    private val localApi: LocalApi by injector.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_claims, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.let {
            it.title = "Mes réclamations"
            it.setDisplayHomeAsUpEnabled(true)
        }

        epoxyController = UserClaimsEpoxyController()

        user_claims.apply {
            adapter = epoxyController.adapter
            layoutManager = LinearLayoutManager(context)
        }

        localApi.getUserById(FirebaseAuth.getInstance().currentUser!!.uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(loader_indicator.isEnabled || loader_indicator.isActivated || loader_indicator.indicator.isRunning) {
                        loader_indicator.hide()
                    }

                    epoxyController.userClaims = it.claims
                    epoxyController.requestModelBuild()

                    Log.v("@getUser", "$it")
                }, {
                    Log.v("@getUser", "${it.printStackTrace()}")
                })

    }

    companion object {
        const val fragmentTag = "userClaims"
    }

}
