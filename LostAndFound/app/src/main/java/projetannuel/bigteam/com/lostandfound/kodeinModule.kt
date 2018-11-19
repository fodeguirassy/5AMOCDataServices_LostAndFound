package projetannuel.bigteam.com.lostandfound

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.jxinject.jxInjectorModule
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton
import projetannuel.bigteam.com.lostandfound.navigation.AppNavigator
import projetannuel.bigteam.com.lostandfound.network.CloudFunctionsService
import projetannuel.bigteam.com.lostandfound.network.LocalApiService
import projetannuel.bigteam.com.lostandfound.network.buildCloudFunctionRetrofit
import projetannuel.bigteam.com.lostandfound.network.buildCloudFunctionService
import projetannuel.bigteam.com.lostandfound.network.LocalApi
import retrofit2.Retrofit

/**
 * kodeinModule -
 * @author guirassy
 * @version $Id$
 */

val kodeinModule = Kodein.Module {

    import(jxInjectorModule)

    bind<MainActivity>() with provider { (instance<Activity>()) as MainActivity }

    bind<Int>("main_container_id") with provider { R.id.main_container }

    bind<AppNavigator>() with singleton { AppNavigator(instance(), instance("main_container_id")) }

    //bind<Retrofit>() with singleton { buildRetrofit() }
    bind<Retrofit>() with singleton { buildCloudFunctionRetrofit() }
    //bind<SNCFNetworkService>() with singleton { buildNetworkService(instance()) }
    bind<CloudFunctionsService>() with singleton { buildCloudFunctionService(instance()) }

    bind<LocalApi>() with singleton { LocalApiService().createService() }

}