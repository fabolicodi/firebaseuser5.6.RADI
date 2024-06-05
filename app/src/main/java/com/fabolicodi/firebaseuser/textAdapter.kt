package com.fabolicodi.firebaseuser

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fabolicodi.firebaseuser.databinding.TextItemBinding

class textAdapter (
    private val tekstlista: ArrayList<firebase>,
    private val th: Context
): RecyclerView.Adapter<textAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): textAdapter.ViewHolder {
        val v=TextItemBinding.inflate(LayoutInflater.from(th),parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: textAdapter.ViewHolder, position: Int) {
        holder.bindItem(tekstlista[position],th)
    }

    override fun getItemCount(): Int {
        return tekstlista.size
    }

    class ViewHolder(private var itemBinding: TextItemBinding):
            RecyclerView.ViewHolder(itemBinding.root){
                fun bindItem(tekst:firebase,th: Context){
                    itemBinding.id.text=tekst.id.toString()
                    itemBinding.ime.text=tekst.ime
                    itemBinding.prezime.text=tekst.prezime
                    itemBinding.nkzl.text=tekst.nkzl
                    itemBinding.lokacija.text=tekst.lokacija

                }
            }


}