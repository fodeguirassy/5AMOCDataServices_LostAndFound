package projetannuel.bigteam.com.lostandfound.ui


import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.FragmentInjector
import com.github.salomonbrys.kodein.instance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_dashboard.founds
import kotlinx.android.synthetic.main.fragment_dashboard.my_lost
import kotlinx.android.synthetic.main.fragment_dashboard.user_claims
import kotlinx.android.synthetic.main.fragment_dashboard.user_email
import kotlinx.android.synthetic.main.fragment_dashboard.user_name
import projetannuel.bigteam.com.lostandfound.R
import projetannuel.bigteam.com.lostandfound.navigation.AppNavigator
import projetannuel.bigteam.com.lostandfound.network.LocalApi
import projetannuel.bigteam.com.lostandfound.network.model.CloudSqlUser


class DashboardFragment : Fragment(), FragmentInjector {

    override val injector = KodeinInjector()
    private val appNavigator: AppNavigator by injector.instance()

    private lateinit var userId: String
    private lateinit var lostAndFoundUser: CloudSqlUser

    private val localApiService: LocalApi by injector.instance()

    companion object {

        private const val UserIdTag = "userId"
        const val fragmentTag: String = "dashboard"

        fun newInstance(userId: String): DashboardFragment {
            val dashboardFragment = DashboardFragment()
            val args = Bundle()
            args.putString(UserIdTag, userId)
            dashboardFragment.arguments = args
            return dashboardFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        userId = arguments.getString(DashboardFragment.UserIdTag)

        localApiService.getUserById(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    lostAndFoundUser = it
                    user_name.text = lostAndFoundUser.displayName
                    user_email.text = lostAndFoundUser.email

                }, {
                    Log.v("@@error", "${it.printStackTrace()}")
                })

        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.hide()

        founds.setOnClickListener {
            appNavigator.displayLostAndFounds()
        }

        user_claims.setOnClickListener {
            appNavigator.displayUserClaims()
        }

        my_lost.setOnClickListener {
            appNavigator.displayUserLossesFragment()
        }
    }
}
