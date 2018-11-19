package projetannuel.bigteam.com.lostandfound.network.model

/**
 * Illustration -
 * @author guirassy
 * @version $Id$
 */

data class Illustration(val totalHits : Int, val hits : List<Hits>)

data class Hits(val webformatURL: String)
