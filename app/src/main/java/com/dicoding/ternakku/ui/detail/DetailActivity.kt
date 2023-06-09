package com.dicoding.ternakku.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.ternakku.ListPenyakitAdapter
import com.dicoding.ternakku.MainActivity
import com.dicoding.ternakku.R
import com.dicoding.ternakku.data.retrofit.ApiConfig
import com.dicoding.ternakku.data.retrofit.response.DiseaseResponse
import com.dicoding.ternakku.data.retrofit.response.ListDiseasesResponseItem
import com.dicoding.ternakku.databinding.ActivityDetailBinding
import com.dicoding.ternakku.ui.favorite.FavoriteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var adapter: ListPenyakitAdapter
    private val list = ArrayList<ListDiseasesResponseItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListPenyakitAdapter(list)
        setViewModel()
        val getDisease = intent.getStringExtra("name")
        if (getDisease != null) {
            getDetail(getDisease)
        }

        val diseaseName = intent.getStringExtra(MainActivity.EXTRA_NAME)
        val diseaseDetails = intent.getStringExtra(MainActivity.EXTRA_DETAIL)

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = diseaseName?.let { viewModel.cekFavoriteDisease(it) }
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0.toString()) {
                        binding.favorite.isChecked = true
                        isChecked = true
                    } else {
                        binding.favorite.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.favorite.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                if (diseaseName != null) {
                    if (diseaseDetails != null) {
                        viewModel.insertFavoriteDisease(
                            diseaseName,
                            diseaseDetails,
                            "handle"
                        )
                    }
                }

            } else {
                if (diseaseName != null) {
                    viewModel.deleteFavoriteDisease(diseaseName)
                }
            }
            binding.favorite.isChecked = isChecked
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(
            this,
        )[DetailViewModel::class.java]
    }

    private fun getDetail(name: String) {
        val client = ApiConfig.getApiService().getDetails(name)
        client.enqueue(object : Callback<DiseaseResponse> {
            override fun onResponse(
                call: Call<DiseaseResponse>,
                response: Response<DiseaseResponse>,
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setDetailsContent(responseBody)
                        Toast.makeText(
                            this@DetailActivity, "Success",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(this@DetailActivity, "Error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<DiseaseResponse>, t: Throwable) {
                Log.e("DetailActivity", "onFailure: ${t.message.toString()}")
                Toast.makeText(this@DetailActivity, "Gagal instance Retrofit", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun setDetailsContent(data: DiseaseResponse) {
        binding.apply {
            tvName.text = data.diseaseName
            tvDescription.text = data.diseaseDetails
            tvHandle.text = data.handlingMethod
        }
    }

//    private fun showLoading(isLoading: Boolean) {
//        if (isLoading){
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val KEY_ID = "name"
        const val EXTRA_NAME = "diseaseName"
        const val EXTRA_DETAIL = "diseaseDetails"
        const val EXTRA_HANDLE = "handlingMethod"
    }
}