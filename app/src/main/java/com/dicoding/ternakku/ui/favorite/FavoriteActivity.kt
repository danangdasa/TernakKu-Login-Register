package com.dicoding.ternakku.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ternakku.FavoriteAdapter
import com.dicoding.ternakku.ListPenyakitAdapter
import com.dicoding.ternakku.data.retrofit.response.DiseaseResponse
import com.dicoding.ternakku.data.retrofit.response.ListDiseasesResponseItem
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDisease
import com.dicoding.ternakku.databinding.ActivityFavoriteBinding
import com.dicoding.ternakku.ui.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: ListPenyakitAdapter
    private lateinit var viewModel: FavoriteViewModel
    private val list = ArrayList<ListDiseasesResponseItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListPenyakitAdapter(list)
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = adapter
        }

        adapter.setItemClickCallback(object : ListPenyakitAdapter.OnItemClickCallback {
            override fun onItemClick(data: FavoriteDisease) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_NAME, data.diseaseName)
                    it.putExtra(DetailActivity.EXTRA_DETAIL, data.diseaseDetails)
                    it.putExtra(DetailActivity.EXTRA_HANDLE, data.handlingMethod)
                    startActivity(it)
                }
            }
        })


        viewModel.getFavoriteDiseases()?.observe(this) {
            if (it != null) {
                val list = getListPenyakit(it)
                adapter.setList(list)
            }
        }
    }

    private fun getListPenyakit(deseases: List<FavoriteDisease>): ArrayList<ListDiseasesResponseItem> {
        val listDesease = ArrayList<ListDiseasesResponseItem>()
        for (desease in deseases) {
            val userMapped = ListDiseasesResponseItem(
                desease.diseaseName,
                desease.diseaseDetails,
                desease.handlingMethod
            )
            listDesease.add(userMapped)
        }
        return listDesease
    }
}