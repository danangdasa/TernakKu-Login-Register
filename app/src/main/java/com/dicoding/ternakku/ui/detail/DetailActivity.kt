package com.dicoding.ternakku.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.dicoding.ternakku.MainActivity
import com.dicoding.ternakku.R
import com.dicoding.ternakku.data.retrofit.Disease
import com.dicoding.ternakku.databinding.ActivityDetailBinding
import com.dicoding.ternakku.ui.favorite.FavoriteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val penyakit = intent.getParcelableExtra<Disease>(MainActivity.EXTRA_NAME) as Disease
        println(penyakit?.name.toString())

        val id = intent.getIntExtra(MainActivity.EXTRA_ID, 0)
        val named = intent.getStringExtra(MainActivity.EXTRA_NAMED)
        val detail = intent.getStringExtra(MainActivity.EXTRA_DETAIL)

        val name = findViewById<TextView>(R.id.tv_name)
        val description =  findViewById<TextView>(R.id.tv_description)
        val handle = findViewById<TextView>(R.id.tv_handle)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        name.text = penyakit.name
        description.text = penyakit.detail
        handle.text = penyakit.heandle

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.cekFavoriteDisease(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count>0){
                        binding.favorite.isChecked = true
                        isChecked = true
                    } else{
                        binding.favorite.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.favorite.setOnClickListener{
            isChecked = !isChecked
            if (isChecked){
                if (named != null) {
                    if (detail != null) {
                        viewModel.insertFavoriteDisease(
                            id,
                            named,
                            detail,
                            "handle")
                    }
                }
            }else{
                viewModel.deleteFavoriteDisease(id)
            }
            binding.favorite.isChecked = isChecked
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> { Intent(this, FavoriteActivity::class.java).also {
                startActivity(it)
            } }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}