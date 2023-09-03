package com.github.xide2

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import java.io.File
import java.io.FileWriter
import java.lang.RuntimeException
import java.lang.StringBuilder
import java.util.Scanner

class EditorActivity : AppCompatActivity() {
    val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)

    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        //load last file
        val lastFile = sharedPreferences.getString("currentFile", null)
        if(lastFile != null)
            findViewById<EditText>(R.id.codeEditText).setText(getFileContent(lastFile))

        //setup optionsLayout
        val optionsLayout = findViewById<LinearLayout>(R.id.optionsLayout)

        //file options
        optionsLayout.findViewById<Button>(R.id.fileButton).setOnClickListener {
            showPopup(R.layout.file_option_layout, R.id.fileButton)
        }
        var options = findViewById<ConstraintLayout>(R.layout.file_option_layout)
        options.findViewById<Button>(R.id.saveButton).setOnClickListener {
            if(sharedPreferences.getString("currentFile", null) != null){
                setFileContent(sharedPreferences.getString("currentFile", null)!!, findViewById<EditText>(R.id.codeEditText).text.toString())
            }else{
                //TODO
            }
        }
        options.findViewById<Button>(R.id.closeButton).setOnClickListener {
            if(sharedPreferences.getString("currentFile", null) != null){
                setFileContent(sharedPreferences.getString("currentFile", null)!!, findViewById<EditText>(R.id.codeEditText).text.toString())
                //TODO
            }
        }

        //git options
        optionsLayout.findViewById<Button>(R.id.gitButton).setOnClickListener {
            showPopup(R.layout.git_options_layout, R.id.gitButton)
        }

        //build options
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

    companion object {
        fun getFileContent(path: String): String {
            val file = File(path)
            if (file.exists()) {
                val content = StringBuilder()
                val scanner = Scanner(file)

                while (scanner.hasNextLine())
                    content.append(scanner.nextLine()).append("\n")

                return content.toString()
            } else {
                throw RuntimeException("file $path does not exist")
            }
        }

        fun setFileContent(path: String, content: String) {
            val file = File(path)
            if (file.exists()) {
                val writer = FileWriter(file, false)
                writer.write(content)
                writer.close()
            } else {
                throw RuntimeException("file $path does not exist")
            }
        }

        fun createFile(path: String, content: String): File {
            val file = File(path)
            if (file.exists()) {
                throw RuntimeException("file $path already exist")
            } else {
                file.createNewFile()

                return file
            }
        }
    }
}