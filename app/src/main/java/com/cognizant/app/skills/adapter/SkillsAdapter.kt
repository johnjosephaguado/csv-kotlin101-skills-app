package com.cognizant.app.skills.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.cognizant.app.skills.R
import com.cognizant.app.skills.data.ConsultantResponse
import com.cognizant.app.skills.data.ConsultantSkillsResponse
import com.cognizant.app.skills.model.Consultant

class SkillsAdapter(private val skills: List<ConsultantSkillsResponse>): RecyclerView.Adapter<SkillsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.name)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.skill_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = skills[position]
        holder.name!!.text = item.skillName
    }

    override fun getItemCount(): Int {
        return skills.size
    }
}
