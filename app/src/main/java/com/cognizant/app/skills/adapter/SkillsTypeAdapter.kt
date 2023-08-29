package com.cognizant.app.skills.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cognizant.app.skills.R
import com.cognizant.app.skills.data.SkillsTypeResponse


class SkillsTypeAdapter(
    private val skillsType: List<SkillsTypeResponse>
) : RecyclerView.Adapter<SkillsTypeAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var logoView: ImageView = view.findViewById(R.id.logo)
        var name: TextView = view.findViewById(R.id.name)
        var context: Context = view.context
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.skill_item, parent, false)

        return ViewHolder(view)
    }

    private fun loadDrawableByName(context: Context, drawableName: String): Drawable? {
        val resourceId = context.resources.getIdentifier(drawableName, "drawable", context.packageName)
        return if (resourceId != 0) {
            ContextCompat.getDrawable(context, resourceId)
        }
        else {
            return null
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = skillsType[position]
        val drawable: Drawable? = loadDrawableByName(holder.context, item.description!!.lowercase())
        if(drawable != null) {
            holder.logoView!!.setImageDrawable(drawable)
        }
        holder.name!!.text = item.description
        holder.name.tag = position

        holder.itemView.apply {
            setOnClickListener {
                onClickListener?.let { it(item) }
            }
        }
    }

    private var onClickListener: ((skillType: SkillsTypeResponse) -> Unit)? = null

    fun setOnClickListener(listener: (skillType: SkillsTypeResponse) -> Unit) {
        onClickListener = listener
    }

    override fun getItemCount(): Int {
        return skillsType.size
    }
}