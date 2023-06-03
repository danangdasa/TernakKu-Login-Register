package com.dicoding.ternakku

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ternakku.data.retrofit.Desease
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDao
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDesease
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDeseaseRoomDatabase
import com.dicoding.ternakku.databinding.ActivityMainBinding
import com.dicoding.ternakku.ui.detail.DetailActivity
import com.dicoding.ternakku.ui.scan.ScanActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity: AppCompatActivity()  {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rView: RecyclerView
    private val list = ArrayList<Desease>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rView = findViewById(R.id.rV_list)
        rView.setHasFixedSize(true)

        list.addAll(getListPenyakit())
        showRecyclerList()

        binding.efbScan.setOnClickListener {
            val intent = Intent(this@MainActivity, ScanActivity::class.java)
            startActivity(intent)
        }

        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }

        })
    }

    private fun getListPenyakit(): ArrayList<Desease> {
        val dataId = resources.getIntArray(R.array.id_penyakit)
        val dataName = resources.getStringArray(R.array.nama_penyakit)
        val dataDescription = resources.getStringArray(R.array.detail_penyakit)
        val dataHeandle = resources.getStringArray(R.array.cara_mengatasi)
        val listPenyakit = ArrayList<Desease>()
        for (i in dataName.indices) {
            val penyakit = Desease(dataId[i],dataName[i], dataDescription[i], dataHeandle[i])
            listPenyakit.add(penyakit)
        }
        return listPenyakit
    }

    private fun showRecyclerList() {
        rView.layoutManager = LinearLayoutManager(this)
        val listPenyakit = ListPenyakitAdapter(list)
        rView.adapter = listPenyakit

        listPenyakit.setOnItemClickCallback(object : ListPenyakitAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Desease) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra(EXTRA_NAME, data)
                intentToDetail.putExtra(EXTRA_ID, data.id)
                startActivity(intentToDetail)
            }
        })
    }


    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAME = "extra_name"
    }


}