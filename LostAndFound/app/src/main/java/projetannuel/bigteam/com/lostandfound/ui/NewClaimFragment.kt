package projetannuel.bigteam.com.lostandfound.ui


import android.os.Bundle
import android.app.Fragment
import android.support.v7.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.fragment_new_claim.loast_object_date
import kotlinx.android.synthetic.main.fragment_new_claim.lost_object_localisation
import kotlinx.android.synthetic.main.fragment_new_claim.lost_object_name
import kotlinx.android.synthetic.main.fragment_new_claim.submit_claim_btn
import kotlinx.android.synthetic.main.fragment_new_claim.user_description

import projetannuel.bigteam.com.lostandfound.R
import projetannuel.bigteam.com.lostandfound.navigation.AppNavigator
import projetannuel.bigteam.com.lostandfound.network.CloudFunctionsService
import projetannuel.bigteam.com.lostandfound.network.LocalApi
import projetannuel.bigteam.com.lostandfound.network.model.CloudSQlClaim
import projetannuel.bigteam.com.lostandfound.network.model.CloudSqlRecord
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class NewClaimFragment : Fragment(), FragmentInjector {

    override val injector = KodeinInjector()
    private val cloudFunctionsService: CloudFunctionsService by injector.instance()
    private val localApi : LocalApi by injector.instance()

    private val appNavigator : AppNavigator by injector.instance()
    private lateinit var recordId: String
    private lateinit var cloudSqlRecord: CloudSqlRecord


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_claim, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.let {
            it.title = "Créer une réclamation"
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        recordId = arguments.getString(NewClaimFragment.recordIdTag)

        localApi.getRecordById(recordId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.v("@@succNewClaim", "${it}")
                    if (loader_indicator.isEnabled || loader_indicator.isActivated || loader_indicator.indicator.isRunning) {
                        loader_indicator.hide()
                    }
                    cloudSqlRecord = it

                    lost_object_name.text = cloudSqlRecord.gc_obo_nature_c
                    lost_object_localisation.text = cloudSqlRecord.gc_obo_gare_origine_r_name
                    loast_object_date.text = cloudSqlRecord.date


                }, {
                    Log.v("@@errNewClaim", "${it.printStackTrace()}")
                })

        submit_claim_btn.setOnClickListener {

            val cloudSQlClaim = CloudSQlClaim(
                    recordId = cloudSqlRecord.recordId,
                    description = user_description.text.toString()
            )

            localApi.saveClaim(cloudSQlClaim, cloudSQlClaim.recordId, FirebaseAuth.getInstance().currentUser!!.uid!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        appNavigator.popBackStack()
                        appNavigator.popBackStack()
                        appNavigator.displayLostAndFounds()
                    }, {
                        Log.v("@@errSaveClaim", "${it.printStackTrace()}")
                    })
        }

    }

    companion object {
        const val fragmentTag = "newClaim"
        private const val recordIdTag = "recordId"

        fun newInstance(recordId: String): NewClaimFragment {

            val fragment = NewClaimFragment()
            val args = Bundle()
            args.putString(NewClaimFragment.recordIdTag, recordId)
            fragment.arguments = args

            return fragment
        }
    }


}
