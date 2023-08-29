package com.cognizant.app.skills

import android.R
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.cognizant.app.skills.data.LevelsResponse

class LevelsFragment(private val levels: List<LevelsResponse>,
                     private val context: Context): DialogFragment() {
    interface LevelDialogListener {
        fun onSelect(level: LevelsResponse)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var levelDescriptions = levels.map { it.description }.toTypedArray()

        return activity?.let {
            val adapter = ArrayAdapter<String>(context, R.layout.simple_expandable_list_item_1, levelDescriptions)

            val builder = AlertDialog.Builder(it)

            builder.setTitle("Update Skill Level")
                .setAdapter(adapter) { _, which ->
                    val listener: LevelDialogListener? = context as? LevelDialogListener
                    listener?.onSelect(levels[which])
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}