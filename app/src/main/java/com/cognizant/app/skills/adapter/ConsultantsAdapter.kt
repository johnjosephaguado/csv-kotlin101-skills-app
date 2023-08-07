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
import com.cognizant.app.skills.model.Consultant

class ConsultantsAdapter(): RecyclerView.Adapter<ConsultantsAdapter.ViewHolder>() {

    var consultantList: MutableList<Consultant> = mutableListOf()

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var avatarView: ImageView = view.findViewById(R.id.avatar)
        var name: TextView = view.findViewById(R.id.name)
        var position: TextView = view.findViewById(R.id.position)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = consultantList[position]
        holder.avatarView!!.setImageResource(getDrawable(item.avatar))
        holder.name!!.text = item.firstName + " " + item.lastName
        holder.position!!.text = item.position
    }

    fun getDrawable(avatar: Int): Int {
        when(avatar) {
            1 -> {
                return R.drawable.avatar1
            }
            2 -> {
                return R.drawable.avatar2
            }
            3 -> {
                return R.drawable.avatar3
            }
            4 -> {
                return R.drawable.avatar4
            }
            5 -> {
                return R.drawable.avatar5
            }
            6 -> {
                return R.drawable.avatar6
            }
        }
        return R.drawable.avatar1
    }

    override fun getItemCount(): Int {
        return consultantList.size
    }
}