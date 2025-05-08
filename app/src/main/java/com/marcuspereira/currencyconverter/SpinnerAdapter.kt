package com.marcuspereira.currencyconverter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class SpinnerAdapter (private val context : Context, private val coins : List<CoinData>) : BaseAdapter(){

    override fun getCount(): Int = coins.size

    override fun getItem(position: Int): CoinData = coins[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView?: LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false)

        val imageView = view.findViewById<ImageView>(R.id.iv_flag)
        val textView = view.findViewById<TextView>(R.id.tv_name)

        val coin = coins[position]

        imageView.setImageResource(coin.icon)
        textView.text = coin.name

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }
}
