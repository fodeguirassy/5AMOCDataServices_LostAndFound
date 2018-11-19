package projetannuel.bigteam.com.lostandfound.network.model

import java.time.LocalDate

/**
 * CloudSQlClaim -
 * @author guirassy
 * @version $Id$
 */

class CloudSQlClaim(val recordId : String,
        val description : String,
        val date : String = "${LocalDate.now()}",
        val status : String = "pending",
        val record: CloudSqlRecord = CloudSqlRecord("","",
                "", "", ""))