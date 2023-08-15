package com.cognizant.app.skills

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cognizant.app.skills.adapter.SkillsAdapter
import com.cognizant.app.skills.data.api.ConsultantInterface
import com.cognizant.app.skills.data.api.RetrofitClient
import com.cognizant.app.skills.databinding.ActivityProfileBinding
import kotlinx.coroutines.launch

class ViewProfileActivity: AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var name: TextView
    private lateinit var position: TextView
    private lateinit var email: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val consultantId = intent.getIntExtra(CONSULTANT_ID, 0)
        name = findViewById(R.id.name)
        position = findViewById(R.id.position)
        email = findViewById(R.id.email)
        if(consultantId > 0) {
            viewConsultantInfo(consultantId)
            viewConsultantSkills(consultantId)
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

    companion object {
        const val CONSULTANT_ID = "CONSULTANT_ID"
    }
}
