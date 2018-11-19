package projetannuel.bigteam.com.lostandfound.ui


import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.FragmentInjector
import com.github.salomonbrys.kodein.instance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_lost_and_found.loader_indicator
import kotlinx.android.synthetic.main.fragment_lost_and_found.lost_and_founds_rv
import projetannuel.bigteam.com.lostandfound.R
import projetannuel.bigteam.com.lostandfound.navigation.AppNavigator
import projetannuel.bigteam.com.lostandfound.network.CloudFunctionsService
import projetannuel.bigteam.com.lostandfound.network.LocalApi
import projetannuel.bigteam.com.lostandfound.network.model.CloudSqlRecord
import projetannuel.bigteam.com.lostandfound.ui.epoxy.LostAndFoundsEpoxyController
import projetannuel.bigteam.com.model.Record
import java.time.LocalDate

class LostAndFoundFragment : Fragment(), FragmentInjector {

    override val injector = KodeinInjector()
    private val appNavigator : AppNavigator by injector.instance()
    private val localApi : LocalApi by injector.instance()

    private var epoxyController = LostAndFoundsEpoxyController ({ dateString -> getLostAndFoundsByDate(dateString) },
            { cloudSqlRecord  -> displayNewClaim(cloudSqlRecord) })

    private var lostAndFoundsRecords = listOf<Record>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lost_and_found, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Les objets trouvés"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.show()

        lost_and_founds_rv.apply {
            adapter = epoxyController.adapter
            layoutManager = LinearLayoutManager(this.context)
        }

        getLostAndFoundsByDate("${LocalDate.now()}")

    }

    private fun getLostAndFoundsByDate(date: String) {

        localApi.getLostAndFoundsByDate(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    if(loader_indicator.isEnabled || loader_indicator.isActivated || loader_indicator.indicator.isRunning) {
                        loader_indicator.hide()
                    }

                    lostAndFoundsRecords = it.records
                    epoxyController.lostAndFoundsRecords = lostAndFoundsRecords
                    epoxyController.requestModelBuild()

                }, {
                    Log.v("@@LostAndFound", "${it.printStackTrace()}")
                })


    }

    companion object {
        const val fragment_tag = "lostAndFound"
    }

    private fun displayNewClaim(cloudSqlRecord: CloudSqlRecord) {

        localApi.saveRecord(cloudSqlRecord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //Log.v("@@succNewClaim", "$it")
                    appNavigator.displayNewClaim(cloudSqlRecord.recordId)
                }, {
                    Log.v("@@errNewClaim", "${it.printStackTrace()}")
                })
    }

}
