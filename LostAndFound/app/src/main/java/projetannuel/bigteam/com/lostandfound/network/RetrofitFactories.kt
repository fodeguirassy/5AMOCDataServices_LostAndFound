package projetannuel.bigteam.com.lostandfound.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * RetrofitFactories -
 * @author guirassy
 * @version $Id$
 */

//https://data.sncf.com/api/records/1.0/search/?dataset=objets-trouves-restitution&rows=10000&q=date%3E=2018-09-03

fun buildOkHttpClient() : OkHttpClient {

    return OkHttpClient.Builder()
            .build()
}

fun buildRetrofit() : Retrofit {
    return Retrofit.Builder()
            .client(buildOkHttpClient())
            .baseUrl("https://data.sncf.com/api/records/1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}


fun buildCloudFunctionService(retrofit: Retrofit): CloudFunctionsService {
    return retrofit.create(CloudFunctionsService::class.java)
}


fun buildNetworkService(retrofit: Retrofit) : SNCFNetworkService {
    return  retrofit.create(SNCFNetworkService::class.java)
}

class LocalApiService {

    fun createService() : LocalApi {

        val retrofit  = Retrofit.Builder()
                .client(buildOkHttpClient())
                .baseUrl("http://10.33.7.21:4444/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(LocalApi::class.java)
    }

}