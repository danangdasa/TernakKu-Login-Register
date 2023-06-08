package com.dicoding.ternakku.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ternakku.ListPenyakitAdapter
import com.dicoding.ternakku.data.retrofit.response.DiseaseResponse
import com.dicoding.ternakku.data.retrofit.response.ListDiseasesResponseItem
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDisease
import com.dicoding.ternakku.databinding.ActivityFavoriteBinding

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