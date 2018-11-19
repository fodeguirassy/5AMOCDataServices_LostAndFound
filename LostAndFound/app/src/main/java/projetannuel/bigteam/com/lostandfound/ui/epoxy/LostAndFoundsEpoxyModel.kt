package projetannuel.bigteam.com.lostandfound.ui.epoxy

import android.net.Uri
import android.support.constraint.ConstraintLayout
import android.util.Log
import com.airbnb.epoxy.EpoxyModel
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.jxinject.jxInjectorModule
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.lost_and_found_list_item.view.button
import kotlinx.android.synthetic.main.lost_and_found_list_item.view.lost_and_found_imgView
import kotlinx.android.synthetic.main.lost_and_found_list_item.view.lost_and_found_localisation
import kotlinx.android.synthetic.main.lost_and_found_list_item.view.lost_and_found_name
import projetannuel.bigteam.com.lostandfound.R
import projetannuel.bigteam.com.lostandfound.kodeinModule
import projetannuel.bigteam.com.lostandfound.network.LocalApi
import projetannuel.bigteam.com.lostandfound.network.model.CloudSqlRecord
import projetannuel.bigteam.com.model.Record

/**
 * LostAndFoundsEpoxyModel -
 * @author guirassy
 * @version $Id$
 */

class LostAndFoundsEpoxyModel(private val record: Record,
        private val onNewClaimClicked: (cloudSqlRecord: CloudSqlRecord) -> Any) :
        EpoxyModel<ConstraintLayout>(), KodeinAware {

    override val kodein = Kodein { import(kodeinModule) }
    private val localApi: LocalApi by lazy {
        kodein.instance<LocalApi>()
    }


    override fun getDefaultLayout(): Int = R.layout.lost_and_found_list_item

    override fun bind(view: ConstraintLayout) {

        val itemNature = record.fields.gc_obo_nature_c
        view.lost_and_found_name.text = record.fields.gc_obo_nature_c
        view.lost_and_found_localisation.text = record.fields.gc_obo_gare_origine_r_name

        val queriedIllustration = itemNature.pickAnIndex()
        var illustrationUri = ""

        localApi.getIllustration(queriedIllustration)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    illustrationUri = it.hits[0].webformatURL
                    if(queriedIllustration == "Lunettes") {
                        illustrationUri = it.hits[1].webformatURL
                    }


                    Picasso.with(view.context)
                            .load(Uri.parse(illustrationUri))
                            .into(view.lost_and_found_imgView)

                }, {
                    Log.v("@@Error Illus", "${it.printStackTrace()}")
                })

        view.button.setOnClickListener {

            val cloudSqlRecord = CloudSqlRecord(
                    recordId = record.recordid,
                    gc_obo_nature_c = record.fields.gc_obo_nature_c,
                    gc_obo_gare_origine_r_name = record.fields.gc_obo_gare_origine_r_name,
                    date = record.fields.date,
                    gc_obo_type_c = record.fields.gc_obo_type_c,
                    illustrationUri = illustrationUri
            )

            onNewClaimClicked(cloudSqlRecord)
        }
    }

}

fun String.pickAnIndex() : String {
    val array = this.split(",", "(", " ")
    if (array.isEmpty()) return this
    if(array[0] == "Manteau" || array[0] == "Carte d'identité" || array[0] == "Porte-monnaie") return array[1]
    return array[0]
}