package com.cognizant.app.skills

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cognizant.app.skills.LevelsFragment.LevelDialogListener
import com.cognizant.app.skills.adapter.SkillsAdapter
import com.cognizant.app.skills.data.ConsultantSkillsResponse
import com.cognizant.app.skills.data.LevelsResponse
import com.cognizant.app.skills.data.SkillsTypeResponse
import com.cognizant.app.skills.data.api.ConsultantInterface
import com.cognizant.app.skills.data.api.LevelsInterface
import com.cognizant.app.skills.data.api.RetrofitClient
import com.cognizant.app.skills.databinding.ActivityProfileBinding
import kotlinx.coroutines.launch
import java.util.ArrayList

class ViewProfileActivity
: AppCompatActivity(), LevelDialogListener {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var name: TextView
    private lateinit var position: TextView
    private lateinit var email: TextView
    private lateinit var btnAddSkills: Button
    private var selectedSkill: ConsultantSkillsResponse? = null
    private lateinit var levels: List<LevelsResponse>
    private var skills: List<ConsultantSkillsResponse> = listOf()
    private var consultantId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        consultantId = intent.getIntExtra(CONSULTANT_ID, 0)
        name = findViewById(R.id.name)
        position = findViewById(R.id.position)
        email = findViewById(R.id.email)
        btnAddSkills = findViewById(R.id.btnAddSkill)

        if(consultantId > 0) {
            viewConsultantInfo(consultantId)
            viewConsultantSkills(consultantId)
        }

        getLevels()

        btnAddSkills.setOnClickListener {
            val intent = Intent(this@ViewProfileActivity, SkillsListActivity::class.java)
            intent.putExtra(SkillsListActivity.CONSULTANT_ID, consultantId)
            intent.putParcelableArrayListExtra(SkillsListActivity.SKILLS, skills as ArrayList<out Parcelable>)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewConsultantSkills(consultantId)
    }

    private fun viewConsultantInfo(consultantId: Int) {
        val retrofitClient = RetrofitClient.getInstance()
        val consultantService = retrofitClient.create(ConsultantInterface::class.java)
        lifecycleScope.launch {
            var response = consultantService.getConsultById(consultantId)
            if(response.isSuccessful) {
                response.body()?.let {
                    name.text = it.firstName + " " + it.lastName
                    position.text = it.designation
                    email.text = it.emailAddress
                }
            }
        }
    }

    private fun viewConsultantSkills(consultantId: Int) {
        val retrofitClient = RetrofitClient.getInstance()
        val consultantService = retrofitClient.create(ConsultantInterface::class.java)
        lifecycleScope.launch {
            var response = consultantService.getConsultSkillsById(consultantId)
            if(response.isSuccessful) {
                response.body()?.let {
                    var skillsAdapter = SkillsAdapter(it)
                    skillsAdapter.setOnClickListener { skill ->
                        selectedSkill = skill
                        val levelDialog = LevelsFragment(levels, this@ViewProfileActivity)
                        levelDialog.show(supportFragmentManager, "test")
                    }
                    skills = it
                    binding.listview.apply {
                        adapter = skillsAdapter
                        setHasFixedSize(true)
                    }
                }
            }
        }
    }

    private fun getLevels() {
        val retrofitClient = RetrofitClient.getInstance()
        val levelsService = retrofitClient.create(LevelsInterface::class.java)
        lifecycleScope.launch {
            var response = levelsService.getLevels()
            if(response.isSuccessful) {
                response.body()?.let {
                    levels = it
                }
            }
        }
    }

    override fun onSelect(level: LevelsResponse) {
        Log.d("View Profile select", "${level.description}")
        updateSkill(level)
    }

    private fun updateSkill(level: LevelsResponse) {
        val retrofitClient = RetrofitClient.getInstance()
        val consultantService = retrofitClient.create(ConsultantInterface::class.java)
        lifecycleScope.launch {
            val skillsResponse = ConsultantSkillsResponse(
                consultantId,
                selectedSkill?.skillId ?: 0,
                selectedSkill?.skillTypeId ?: 0,
                selectedSkill?.skillName ?: "",
                selectedSkill?.years ?: 0,
                level.id,
                level.description
            )

            val response = consultantService.addSkill(skillsResponse)
            if(response.isSuccessful) {
                response.body()?.let {
                    viewConsultantSkills(consultantId)
                }
            }
        }
    }

    companion object {
        const val CONSULTANT_ID = "CONSULTANT_ID"
    }
}


