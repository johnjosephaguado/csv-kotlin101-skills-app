package com.cognizant.app.skills

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cognizant.app.skills.adapter.SkillsTypeAdapter
import com.cognizant.app.skills.data.ConsultantSkillsResponse
import com.cognizant.app.skills.data.SkillsTypeResponse
import com.cognizant.app.skills.data.api.ConsultantInterface
import com.cognizant.app.skills.data.api.RetrofitClient
import com.cognizant.app.skills.data.api.SkillsTypeInterface
import com.cognizant.app.skills.databinding.SkillListBinding
import kotlinx.coroutines.launch

class SkillsListActivity(): AppCompatActivity() {
    private lateinit var binding: SkillListBinding
    private lateinit var skillsTypeList: List<SkillsTypeResponse>
    private lateinit var filteredList: List<SkillsTypeResponse>
    private lateinit var filter: String
    private var consultantId: Int = 0
    private lateinit var consultantSkills: List<ConsultantSkillsResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        consultantId = intent.getIntExtra(CONSULTANT_ID, 0)

        consultantSkills = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra(SKILLS, ConsultantSkillsResponse::class.java) as List<ConsultantSkillsResponse>
        } else {
            intent.getParcelableArrayListExtra<ConsultantSkillsResponse>(SKILLS) as List<ConsultantSkillsResponse>
        }

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

        val skillsTypeAdapter = getSkillsTypeAdapter(filteredList)
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
                    skillsTypeList = it.filterNot {
                        consultantSkills.any { skill ->
                            skill.skillTypeId == it.id
                        }
                    }
                    val skillsTypeAdapter = getSkillsTypeAdapter(skillsTypeList)
                    binding.listview.apply {
                        adapter = skillsTypeAdapter
                        setHasFixedSize(true)
                    }
                }
            }
        }
    }

    private fun getSkillsTypeAdapter(
        skillsTypeList: List<SkillsTypeResponse>
    ) : SkillsTypeAdapter {
        var skillsTypeAdapter = SkillsTypeAdapter(skillsTypeList)
        skillsTypeAdapter.setOnClickListener { skillType ->
            addSkills(skillType)
            Toast.makeText(this, "Adding skill: ${skillType.description}", Toast.LENGTH_SHORT).show()
        }

        return skillsTypeAdapter
    }

    private fun addSkills(skillType: SkillsTypeResponse) {
        val retrofitClient = RetrofitClient.getInstance()
        val consultantService = retrofitClient.create(ConsultantInterface::class.java)
        lifecycleScope.launch {
            val skillsResponse = ConsultantSkillsResponse(
                consultantId,
                null,
                skillType.id,
                null,
                1,
                1,
                null
            )

            val response = consultantService.addSkill(skillsResponse)
            if(response.isSuccessful) {
                response.body()?.let {

                }
            }
        }
    }

    companion object {
        const val CONSULTANT_ID = "CONSULTANT_ID"
        const val SKILLS = "SKILLS"
    }
}