package projetannuel.bigteam.com.lostandfound.network

import io.reactivex.Observable
import projetannuel.bigteam.com.model.RawResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * SNCFNetworkService -
 * @author guirassy
 * @version $Id$
 */

interface SNCFNetworkService {
    @GET("search/")
    fun getLostAndFounds(@Query("dataset", encoded=true)query:String, @Query("q", encoded = true) queryParam: String): Observable<RawResponse>


}