package projetannuel.bigteam.com.lostandfound.ui.userclaims

import android.net.Uri
import android.support.constraint.ConstraintLayout
import com.airbnb.epoxy.EpoxyModel
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.instance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_claims_item.view.claim_description
import kotlinx.android.synthetic.main.user_claims_item.view.claim_image
import kotlinx.android.synthetic.main.user_claims_item.view.claim_localisation
import kotlinx.android.synthetic.main.user_claims_item.view.claim_name
import kotlinx.android.synthetic.main.user_claims_item.view.claim_status
import projetannuel.bigteam.com.lostandfound.R
import projetannuel.bigteam.com.lostandfound.kodeinModule
import projetannuel.bigteam.com.lostandfound.network.LocalApi
import projetannuel.bigteam.com.lostandfound.network.model.CloudSQlClaim

/**
 * UserClaimsEpoxyModel -
 * @author guirassy
 * @version $Id$
 */
class UserClaimsEpoxyModel(private val cloudSQlClaim: CloudSQlClaim) :
        EpoxyModel<ConstraintLayout>(), KodeinAware {

    override val kodein = Kodein { import(kodeinModule) }
    private val localApi: LocalApi by lazy {
        kodein.instance<LocalApi>()
    }

    override fun getDefaultLayout(): Int  = R.layout.user_claims_item

    override fun bind(view: ConstraintLayout) {
        view.claim_name.text = cloudSQlClaim.record.gc_obo_nature_c
        view.claim_description.text = cloudSQlClaim.description
        view.claim_localisation.text = cloudSQlClaim.record.gc_obo_gare_origine_r_name
        view.claim_status.text = cloudSQlClaim.status
        Picasso.with(view.context)
                .load(Uri.parse(cloudSQlClaim.record.illustrationUri))
                .into(view.claim_image)
    }
}