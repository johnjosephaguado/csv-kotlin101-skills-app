package com.cognizant.app.skills

import android.R
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.cognizant.app.skills.data.LevelsResponse

class LevelsFragment(private val levels: List<LevelsResponse>,
                     private val context: Context): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var levelDescriptions = levels.map { it.description }.toTypedArray()

        return activity?.let {
            val adapter = ArrayAdapter<String>(context, R.layout.simple_expandable_list_item_1, levelDescriptions)

            val builder = AlertDialog.Builder(it)

            builder.setTitle("Update Skill Level")
                .setAdapter(adapter,
                    { dialog, which ->
                        // The 'which' argument contains the index position
                        // of the selected item
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}