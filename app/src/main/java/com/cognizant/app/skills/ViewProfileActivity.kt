package com.cognizant.app.skills

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cognizant.app.skills.adapter.SkillsAdapter
import com.cognizant.app.skills.data.LevelsResponse
import com.cognizant.app.skills.data.api.ConsultantInterface
import com.cognizant.app.skills.data.api.LevelsInterface
import com.cognizant.app.skills.data.api.RetrofitClient
import com.cognizant.app.skills.databinding.ActivityProfileBinding
import kotlinx.coroutines.launch
class ViewProfileActivity: AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var name: TextView
    private lateinit var position: TextView
    private lateinit var email: TextView
    private lateinit var btnAddSkills: Button
    private var allowEditing = false
    private lateinit var levels: List<LevelsResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val consultantId = intent.getIntExtra(CONSULTANT_ID, 0)
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
            startActivity(intent)
        }
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
                    var consultantAdapter = SkillsAdapter(it)
                    binding.listview.apply {
                        adapter = consultantAdapter
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

                    val levelDialog = LevelsFragment(levels, this@ViewProfileActivity)
                    levelDialog.show(supportFragmentManager, "test")
                }
            }
        }
    }

    companion object {
        const val CONSULTANT_ID = "CONSULTANT_ID"
    }
}


