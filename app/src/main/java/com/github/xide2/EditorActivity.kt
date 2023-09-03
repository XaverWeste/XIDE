package com.github.xide2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow

class EditorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        val optionsLayout = findViewById<LinearLayout>(R.id.optionsLayout)

        optionsLayout.findViewById<Button>(R.id.fileButton).setOnClickListener {
            showPopup(R.layout.file_option_layout, R.id.fileButton)
        }
        optionsLayout.findViewById<Button>(R.id.gitButton).setOnClickListener {
            showPopup(R.layout.git_options_layout, R.id.gitButton)
        }
        optionsLayout.findViewById<Button>(R.id.buildButton).setOnClickListener {
            showPopup(R.layout.build_option_layout, R.id.buildButton)
        }
    }

    private fun showPopup(optionLayout: Int, anchorButton: Int){
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val popupView = inflater.inflate(optionLayout, null)
        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true)

        val button = findViewById<Button>(anchorButton)
        popupWindow.showAsDropDown(button)
    }
}