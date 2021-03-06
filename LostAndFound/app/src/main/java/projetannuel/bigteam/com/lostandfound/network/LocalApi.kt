package projetannuel.bigteam.com.lostandfound.network

import io.reactivex.Observable
import projetannuel.bigteam.com.lostandfound.network.model.CloudSQlClaim
import projetannuel.bigteam.com.lostandfound.network.model.CloudSqlRecord
import projetannuel.bigteam.com.lostandfound.network.model.CloudSqlUser
import projetannuel.bigteam.com.lostandfound.network.model.Illustration
import projetannuel.bigteam.com.model.RawResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * LocalApiService -
 * @author guirassy
 * @version $Id$
 */

interface LocalApi {

    @GET("founds")
    fun getLostAndFoundsByDate(@Query("date") dString: String) : Observable<RawResponse>

    @POST("user")
    fun saveCloudSqlUser(@Body cloudSqlUser: CloudSqlUser) : Observable<Any>

    @GET("user")
    fun getUserById(@Query("userId") userId:String) : Observable<CloudSqlUser>

    @GET("records")
    fun getRecords(): Observable<List<CloudSqlRecord>>

    @GET("record")
    fun getRecordById(@Query ("recordId") recordId: String): Observable<CloudSqlRecord>

    @POST("record")
    fun saveRecord(@Body cloudSqlRecord: CloudSqlRecord) : Observable<Any>

    @POST("claim")
    fun saveClaim(@Body cloudSQlClaim: CloudSQlClaim, @Query("recordId") recordId: String,
            @Query("userId") userId: String) : Observable<Any>

    @GET("illustration")
    fun getIllustration(@Query ("item") item:String) : Observable<Illustration>

}