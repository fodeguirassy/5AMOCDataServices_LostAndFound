package projetannuel.bigteam.com.lostandfound.network.model

/**
 * CloudSqlRecord -
 * @author guirassy
 * @version $Id$
 */

data class CloudSqlRecord(val recordId: String,
        val gc_obo_nature_c : String,
        val gc_obo_gare_origine_r_name : String,
        val date : String,
        val gc_obo_type_c: String,
        val illustrationUri : String = "")