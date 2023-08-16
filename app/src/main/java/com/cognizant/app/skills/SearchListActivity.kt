package com.cognizant.app.skills

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cognizant.app.skills.adapter.ConsultantsAdapter
import com.cognizant.app.skills.data.ConsultantResponse
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
                    searchConsultants(v.text.toString())
                    true
                }
                else -> false
            }
        }
        getConsultants()
    }

    private fun displayResults(results: List<ConsultantResponse>) {
        var consultantAdapter = ConsultantsAdapter(results)
        consultantAdapter.setOnClickListener { consultantId ->
            val intent = Intent(this@SearchListActivity, ViewProfileActivity::class.java)
            intent.putExtra(ViewProfileActivity.CONSULTANT_ID, consultantId)
            startActivity(intent)
        }
        binding.listview.apply {
            adapter = consultantAdapter
            setHasFixedSize(true)
        }
        searchCount.text = "we found " + results.size + " consultant(s)"
    }

    private fun searchConsultants(skill: String) {
        val retrofitClient = RetrofitClient.getInstance()
        val consultantService = retrofitClient.create(ConsultantInterface::class.java)
        lifecycleScope.launch {
            var response = consultantService.searchConsultantsBySkill(skill)
            if(response.isSuccessful) {
                response.body()?.let {
                    displayResults(it)
                    searchLabel.text = "Search results for " + skill
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
                        displayResults(it)
                    }
                }
            }
        }
    }
}

