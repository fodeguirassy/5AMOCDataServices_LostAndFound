package projetannuel.bigteam.com.lostandfound.ui.epoxy

import android.support.constraint.ConstraintLayout
import android.util.Log
import com.airbnb.epoxy.EpoxyModel
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import kotlinx.android.synthetic.main.spinner_item.view.pick_a_date_ed
import projetannuel.bigteam.com.lostandfound.R
import java.time.LocalDate

/**
 * SpinnerEpoxyModel -
 * @author guirassy
 * @version $Id$
 */

class SpinnerEpoxyModel(private val onNewDatePicked : (dateString : String) -> Any) : EpoxyModel<ConstraintLayout>() {

    override fun getDefaultLayout(): Int = R.layout.spinner_item

    override fun bind(view: ConstraintLayout) {
        super.bind(view)

        val currentDate = LocalDate.now()

        view.pick_a_date_ed.apply {
            hint = "$currentDate"
            setOnClickListener {
                SpinnerDatePickerDialogBuilder()
                        .context(view.context)
                        .callback{_, year: Int, monthOfYear: Int, dayOfMonth: Int ->

                            val montString = "0".plus("${monthOfYear + 1}").takeIf { monthOfYear < 10 } ?: "$monthOfYear"
                            val dayString = "0".plus("$dayOfMonth").takeIf { dayOfMonth < 10 } ?: "$dayOfMonth"
                            val dString = "$year".plus("-$montString").plus("-$dayString")

                            onNewDatePicked(dString)
                            setText("$year".plus("-$montString").plus("-$dayString"))
                        }
                        .spinnerTheme(R.style.NumberPickerStyle)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(currentDate.year, currentDate.monthValue, currentDate.dayOfMonth)
                        .maxDate(currentDate.year, currentDate.monthValue, currentDate.dayOfMonth)
                        .minDate(2000,0, 1)
                        .build()
                        .show()
            }
        }

    }


}