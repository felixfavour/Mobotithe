package com.felixfavour.mobotithe.gui.view.income

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.IncomeCategoryItemBinding

class IncomeCategoryAdapter(context: Context, resource: Array<String>) : ArrayAdapter<String>(context, R.layout.income_category_item, resource) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: IncomeCategoryItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.income_category_item, parent, false)
        val textItem = getItem(position)

        binding.categoryText.text = textItem

        return binding.root
    }
}