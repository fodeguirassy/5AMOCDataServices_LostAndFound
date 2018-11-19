package projetannuel.bigteam.com.lostandfound

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy

/**
 * LostAndFoundApplication -
 * @author guirassy
 * @version $Id$
 */
class LostAndFoundApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        bind<Application>() with instance(this@LostAndFoundApplication)
        import(kodeinModule)
    }

}