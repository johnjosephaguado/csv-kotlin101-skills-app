package com.cognizant.app.skills

import android.os.Bundle
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cognizant.app.skills.adapter.SkillsTypeAdapter
import com.cognizant.app.skills.data.SkillsTypeResponse
import com.cognizant.app.skills.data.api.RetrofitClient
import com.cognizant.app.skills.data.api.SkillsTypeInterface
import com.cognizant.app.skills.databinding.SkillListBinding
import kotlinx.coroutines.launch

class SkillsListActivity: AppCompatActivity() {
    private lateinit var binding: SkillListBinding
    private lateinit var skillsTypeList: List<SkillsTypeResponse>
    private lateinit var filteredList: List<SkillsTypeResponse>
    private lateinit var filter: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SkillListBinding.inflate(layoutInflater)
        binding.listview.layoutManager = LinearLayoutManager(this)

        binding.searchBar.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filter(query)

                if (filteredList.isEmpty()){
                    Toast.makeText(this@SkillsListActivity, "No Match found", Toast.LENGTH_LONG).show()
                }

                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })
        setContentView(binding.root)
        getSkills()
    }

    fun filter(query: String) {
        filter = query
        filteredList = skillsTypeList.filter { it.description.startsWith(filter, ignoreCase = true) }

        val skillsTypeAdapter = SkillsTypeAdapter(filteredList)
        binding.listview.apply {
            adapter = skillsTypeAdapter
            setHasFixedSize(true)
        }
    }

    private fun getSkills() {
        val retrofitClient = RetrofitClient.getInstance()
        val skillsTypeInterface = retrofitClient.create(SkillsTypeInterface::class.java)
        lifecycleScope.launch {
            var response = skillsTypeInterface.getSkillTypes()
            if(response.isSuccessful) {
                response.body()?.let {
                    skillsTypeList = it
                    val skillsTypeAdapter = SkillsTypeAdapter(skillsTypeList)
                    binding.listview.apply {
                        adapter = skillsTypeAdapter
                        setHasFixedSize(true)
                    }
                }
            }
        }
    }
}