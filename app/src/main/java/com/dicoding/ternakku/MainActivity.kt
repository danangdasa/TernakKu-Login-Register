package com.dicoding.ternakku

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ternakku.data.retrofit.ApiConfig
import com.dicoding.ternakku.data.retrofit.response.ListDiseasesResponse
import com.dicoding.ternakku.data.retrofit.response.ListDiseasesResponseItem
import com.dicoding.ternakku.databinding.ActivityMainBinding
import com.dicoding.ternakku.preference.LoginPreference
import com.dicoding.ternakku.ui.favorite.FavoriteActivity
import com.dicoding.ternakku.ui.login.LoginActivity
import com.dicoding.ternakku.ui.scan.ScanActivity
import com.dicoding.ternakku.viewmodelfactory.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataSetting")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewModel()
        val layoutManager = LinearLayoutManager(this)
        binding.rVList.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rVList.addItemDecoration(itemDecoration)


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

        binding.btnToLogin.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getLoginUser().observe(this) { userData ->
            val tokken = userData.token
            getListData(tokken)
        }
    }

    private fun setViewModel(){
        val pref = LoginPreference.getInstance(dataStore)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(pref)
        )[MainViewModel::class.java]

        mainViewModel.getLoginUser().observe(this) { user ->
            if (user.isLogin) {

            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

        }
    }

    private fun getListData(token: String){
        showLoading(true)
        val client = ApiConfig.getApiService().getList("Bearer " + token)
        client.enqueue(object : Callback<ListDiseasesResponse> {
            override fun onResponse(
                call: Call<ListDiseasesResponse>,
                response: Response<ListDiseasesResponse>
            ) {
                showLoading(false)
                if(response.isSuccessful){
                    val responsBody = response.body()
                    if (responsBody!= null){
                        setData(responsBody.listDiseasesResponse as List<ListDiseasesResponseItem>)
                        Toast.makeText(this@MainActivity, responsBody.toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ListDiseasesResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                Toast.makeText(this@MainActivity, "Gagal instance Retrofit", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setData(listPenyakit: List<ListDiseasesResponseItem>){
        val adapter = ListPenyakitAdapter(listPenyakit)
        binding.rVList.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> {
                startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
            }
            R.id.logout -> {
                mainViewModel.logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val TAG = "MainActivity"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_NAMED = "extra_named"
        const val EXTRA_DETAIL = "extra_detail"
    }


}