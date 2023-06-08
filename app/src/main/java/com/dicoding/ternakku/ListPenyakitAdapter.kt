package com.dicoding.ternakku

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ternakku.data.retrofit.response.DiseaseResponse
import com.dicoding.ternakku.data.retrofit.response.ListDiseasesResponseItem
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDisease
import com.dicoding.ternakku.databinding.ListPenyakitBinding
import com.dicoding.ternakku.ui.detail.DetailActivity

class ListPenyakitAdapter (private var listPenyakit : ArrayList<ListDiseasesResponseItem>)  : RecyclerView.Adapter<ListPenyakitAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(private val binding: ListPenyakitBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(listPenyakit: ListDiseasesResponseItem){
            binding.tvName.text =listPenyakit.diseaseName
            binding.tvDescription.text=listPenyakit.diseaseDetails
        }
    }
    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ListPenyakitBinding.inflate(LayoutInflater.from(view.context), view, false)
        return ViewHolder(itemBinding)
    }
    override fun getItemCount(): Int {
        return listPenyakit.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getListPenyakit = listPenyakit[position]
        holder.bind(getListPenyakit)

        holder.itemView.setOnClickListener {
            val moveToDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            moveToDetail.putExtra(DetailActivity.KEY_ID, getListPenyakit.diseaseName)
            holder.itemView.context.startActivity(moveToDetail)
        }
    }

    fun setList(listPenyakit: ArrayList<ListDiseasesResponseItem>) {
        listPenyakit.clear()
        listPenyakit.addAll(listPenyakit)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClick(data: FavoriteDisease)
    }

}