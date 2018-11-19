package projetannuel.bigteam.com.lostandfound.network.model

/**
 * CloudSqlUser -
 * @author guirassy
 * @version $Id$
 */

data class CloudSqlUser(val userId: String, val email : String,
        val password: String = "JKHS8ZOA98878", val displayName : String,
        val photoUrl : String, val isAdmin: Boolean = false,
        val claims : MutableList<CloudSQlClaim> = mutableListOf())