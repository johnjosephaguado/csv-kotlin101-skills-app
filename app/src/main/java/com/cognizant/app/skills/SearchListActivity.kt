package com.cognizant.app.skills

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cognizant.app.skills.adapter.ConsultantsAdapter
import com.cognizant.app.skills.databinding.ActivitySearchBinding
import com.cognizant.app.skills.model.Consultant

class SearchListActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: ConsultantsAdapter
    private lateinit var consultantList: MutableList<Consultant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        consultantList = mutableListOf<Consultant>(
            Consultant(AvatarPicker().pick(),"Florentino", "Ortega", "Senior Software Engineer 2"),
            Consultant(AvatarPicker().pick(),"Kenneth", "Uy", "Software Engineer"),
            Consultant(AvatarPicker().pick(),"Kevin", "Bahia", "Senior Software Engineer 1"),
            Consultant(AvatarPicker().pick(),"Carlo Jayson", "Opena", "Lead Software Engineer")
        )
        adapter = ConsultantsAdapter()
        adapter.consultantList = consultantList
        binding.listview.layoutManager = LinearLayoutManager(this)
        binding.listview.setHasFixedSize(true)
        binding.listview.adapter = adapter
    }
}

class AvatarPicker {
    fun pick(): Int {
        return (1..6).random()
    }
}
