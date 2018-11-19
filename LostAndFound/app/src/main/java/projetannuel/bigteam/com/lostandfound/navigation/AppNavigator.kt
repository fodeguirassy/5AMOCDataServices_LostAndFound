package projetannuel.bigteam.com.lostandfound.navigation

import android.app.FragmentManager
import projetannuel.bigteam.com.lostandfound.ui.DashboardFragment
import projetannuel.bigteam.com.lostandfound.ui.LostAndFoundFragment
import projetannuel.bigteam.com.lostandfound.ui.NewClaimFragment
import projetannuel.bigteam.com.lostandfound.ui.RegisterFragment
import projetannuel.bigteam.com.lostandfound.ui.userclaims.UserClaimsFragment
import projetannuel.bigteam.com.lostandfound.ui.userlosts.UserLossesFragment

/**
 * AppNavigator -
 * @author guirassy
 * @version $Id$
 */


class AppNavigator(private val fragmentManager: FragmentManager,
        private val containerId: Int){

    fun displayLostAndFounds() {
        var fragment = fragmentManager.findFragmentByTag(LostAndFoundFragment.fragment_tag)
        if(fragment == null) {
            fragment = LostAndFoundFragment()
        }
        fragmentManager
                .beginTransaction()
                .replace(containerId,fragment)
                .addToBackStack(LostAndFoundFragment.fragment_tag)
                .commit()
    }

    fun displayDashboard(userId: String) {
        var dashboardFragment = fragmentManager.findFragmentByTag(DashboardFragment.fragmentTag)
        if(dashboardFragment == null) {
            dashboardFragment = DashboardFragment.newInstance(userId)
        }
        fragmentManager
                .beginTransaction()
                .replace(containerId, dashboardFragment)
                .addToBackStack(DashboardFragment.fragmentTag)
                .commit()

    }

    fun displayRegister() {
        fragmentManager
                .beginTransaction()
                .replace(containerId, RegisterFragment())
                .commit()
    }

    fun displayUserClaims() {
        var fragment = fragmentManager.findFragmentByTag(UserClaimsFragment.fragmentTag)
        if(fragment == null) {
            fragment = UserClaimsFragment()
        }
        fragmentManager
                .beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(UserClaimsFragment.fragmentTag)
                .commit()
    }

    fun displayNewClaim(recordId : String) {

        var fragment = fragmentManager.findFragmentByTag(NewClaimFragment.fragmentTag)

        if(fragment == null) {
            fragment = NewClaimFragment.newInstance(recordId)
        }

        fragmentManager
                .beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(NewClaimFragment.fragmentTag)
                .commit()
    }

    fun popBackStack() {
        fragmentManager.popBackStack()
    }

    fun displayUserLossesFragment() {

        var fragment = fragmentManager.findFragmentByTag(UserLossesFragment.fragmentTag)
        if(fragment == null) {
            fragment = UserLossesFragment()
        }

        fragmentManager
                .beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(UserLossesFragment.fragmentTag)
                .commit()
    }
}