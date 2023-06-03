package com.dicoding.ternakku.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ternakku.ListPenyakitAdapter
import com.dicoding.ternakku.data.retrofit.Desease
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDao
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDesease
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDeseaseRoomDatabase
import com.dicoding.ternakku.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: ListPenyakitAdapter
    private lateinit var viewModel: FavoriteViewModel
    private val list = ArrayList<Desease>()

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

        viewModel.getFavoriteDeseases()?.observe(this) {
            if (it != null) {
                val list = getListPenyakit(it)
                adapter.setList(list)
            }
        }

    }
    private fun getListPenyakit(deseases: List<FavoriteDesease>): ArrayList<Desease> {
        val listDesease = ArrayList<Desease>()
        for (desease in deseases) {
            val userMapped = Desease(
                desease.id,
                desease.name,
                desease.detail,
                desease.handle
            )
            listDesease.add(userMapped)
        }
        return listDesease
    }


}