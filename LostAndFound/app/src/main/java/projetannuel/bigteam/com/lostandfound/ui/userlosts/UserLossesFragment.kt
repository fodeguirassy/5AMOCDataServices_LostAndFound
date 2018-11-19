package projetannuel.bigteam.com.lostandfound.ui.userlosts


import android.os.Bundle
import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.FragmentInjector
import com.github.salomonbrys.kodein.instance

import projetannuel.bigteam.com.lostandfound.R
import projetannuel.bigteam.com.lostandfound.navigation.AppNavigator
import projetannuel.bigteam.com.lostandfound.network.LocalApi

class UserLossesFragment : Fragment(), FragmentInjector {


    override val injector = KodeinInjector()
    private val appNavigator : AppNavigator by injector.instance()
    private val localApi : LocalApi by injector.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_losts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.let {
            it.title = "Mes objets perdus"
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.show()
        }
    }

    companion object {
        const val fragmentTag = "userLosses"
    }
}
