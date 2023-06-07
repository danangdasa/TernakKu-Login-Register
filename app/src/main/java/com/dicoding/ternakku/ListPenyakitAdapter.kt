package com.dicoding.ternakku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ternakku.data.retrofit.Disease

class ListPenyakitAdapter (private var listPenyakit : ArrayList<Disease>)  : RecyclerView.Adapter<ListPenyakitAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback : OnItemClickCallback


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(user: ArrayList<Disease>) {
        listPenyakit.clear()
        listPenyakit.addAll(user)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_penyakit, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (id, name, description) = listPenyakit[position]
        holder.tvName.text = name
        holder.tvDescription.text = description
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listPenyakit[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listPenyakit.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Disease)
    }

}