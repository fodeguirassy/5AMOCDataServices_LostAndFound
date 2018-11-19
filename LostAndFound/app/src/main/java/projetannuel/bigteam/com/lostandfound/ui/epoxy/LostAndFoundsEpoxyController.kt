package projetannuel.bigteam.com.lostandfound.ui.epoxy

import com.airbnb.epoxy.EpoxyController
import projetannuel.bigteam.com.lostandfound.network.model.CloudSqlRecord
import projetannuel.bigteam.com.model.Record

/**
 * LostAndFoundsEpoxyController -
 * @author guirassy
 * @version $Id$
 */

class LostAndFoundsEpoxyController(private val onNewDatePicked: (dateString : String) -> Any,
        private val onNewClaim: (cloudSqlRecord: CloudSqlRecord) -> Any): EpoxyController() {

    var lostAndFoundsRecords : List<Record>? = null

    override fun buildModels() {

        SpinnerEpoxyModel(onNewDatePicked)
                .id("SpinnerModel")
                .addTo(this)

        lostAndFoundsRecords?.let {
            it.forEach {
                LostAndFoundsEpoxyModel(it, onNewClaim)
                        .id(it.recordid)
                        .addTo(this)
            }
        }
    }


}