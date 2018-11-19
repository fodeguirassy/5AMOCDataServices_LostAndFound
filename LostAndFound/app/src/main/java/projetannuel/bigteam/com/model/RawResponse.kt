package projetannuel.bigteam.com.model

/**
 * RawResponse -
 * @author guirassy
 * @version $Id$
 */

data class RawResponse(val records : List<Record>,
         val nhits: Int)

data class Record(val fields: Fields,
         val recordid: String)

data class Fields(val gc_obo_nature_c : String,
         val gc_obo_gare_origine_r_name : String,
         val date : String,
         val gc_obo_type_c: String)