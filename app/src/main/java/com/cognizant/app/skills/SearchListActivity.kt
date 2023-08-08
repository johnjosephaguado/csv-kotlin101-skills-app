package com.cognizant.app.skills

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cognizant.app.skills.adapter.ConsultantsAdapter
import com.cognizant.app.skills.data.api.ConsultantInterface
import com.cognizant.app.skills.data.api.RetrofitClient
import com.cognizant.app.skills.databinding.ActivitySearchBinding
import kotlinx.coroutines.launch

class SearchListActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchCount: TextView
    private lateinit var searchInput: EditText
    private lateinit var searchLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.listview.layoutManager = LinearLayoutManager(this)
        searchLabel = findViewById(R.id.searchLabel)
        searchCount = findViewById(R.id.searchCount)
        searchInput = findViewById(R.id.searchInput)
        searchInput.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when(actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    Log.d("action search", v.text.toString())
                    searchConsultants(v.text.toString())
                    true
                }
                else -> false
            }
        }
        getConsultants()
    }

    private fun searchConsultants(skill: String) {
        val retrofitClient = RetrofitClient.getInstance()
        val consultantService = retrofitClient.create(ConsultantInterface::class.java)
        lifecycleScope.launch {
            var response = consultantService.searchConsultantsBySkill(skill)
            if(response.isSuccessful) {
                response.body()?.let {
                    var consultantAdapter = ConsultantsAdapter(it)
                    binding.listview.apply {
                        adapter = consultantAdapter
                        setHasFixedSize(true)
                    }
                    searchLabel.text = "Search results for " + skill
                    searchCount.text = "we found " + it.size + " consultant(s)"
                }
            }
        }

    }

    private fun getConsultants() {
        val retrofitClient = RetrofitClient.getInstance()
        val consultantService = retrofitClient.create(ConsultantInterface::class.java)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                var response = consultantService.getConsultants()
                if(response.isSuccessful) {
                    response.body()?.let {
                        var consultantAdapter = ConsultantsAdapter(it)
                        binding.listview.apply {
                            adapter = consultantAdapter
                            setHasFixedSize(true)
                        }
                        searchCount.text = "we found " + it.size + " consultant(s)"
                    }
                    Log.d("API response", "${response.body()}")
                }
            }
        }
    }
}

