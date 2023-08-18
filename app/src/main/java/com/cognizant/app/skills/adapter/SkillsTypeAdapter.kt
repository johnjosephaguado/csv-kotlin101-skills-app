package com.cognizant.app.skills.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.cognizant.app.skills.R
import com.cognizant.app.skills.data.SkillsTypeResponse

class SkillsTypeAdapter(private val skillsType: List<SkillsTypeResponse>) : RecyclerView.Adapter<SkillsTypeAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.name)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.skill_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = skillsType[position]
        holder.name!!.text = item.description
    }

    override fun getItemCount(): Int {
        return skillsType.size
    }
}