package projetannuel.bigteam.com.lostandfound.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.spinner_single_date_item.view.spinner_date_item_tv

/**
 * DatesSpinnerAdapter -
 * @author guirassy
 * @version $Id$
 */

class DatesSpinnerAdapter(private val mContext: Context,
        private val layoutId : Int,
        private val dates: List<String>) : ArrayAdapter<String>(mContext, layoutId, 0, dates) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createItemView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(mContext).inflate(layoutId, parent, false)
        Log.v("&inflated", "inflate")
        view.spinner_date_item_tv.text = dates[position]
        return view
    }

}