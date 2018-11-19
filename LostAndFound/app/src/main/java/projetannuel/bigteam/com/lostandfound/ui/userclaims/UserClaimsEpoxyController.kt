package projetannuel.bigteam.com.lostandfound.ui.userclaims

import com.airbnb.epoxy.EpoxyController
import projetannuel.bigteam.com.lostandfound.network.model.CloudSQlClaim

/**
 * UserClaimsEpoxyController -
 * @author guirassy
 * @version $Id$
 */
class UserClaimsEpoxyController : EpoxyController() {

    var userClaims : List<CloudSQlClaim>? = null

    override fun buildModels() {
        userClaims?.let {
            it.forEach {
                UserClaimsEpoxyModel(it)
                        .id(it.record.recordId)
                        .addTo(this)
            }
        }
    }
}