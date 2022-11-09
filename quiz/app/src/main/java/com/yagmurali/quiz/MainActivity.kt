package com.yagmurali.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btnStart)
        val edName = findViewById<EditText>(R.id.edText)

        btnStart.setOnClickListener {
            if (edName.text.isEmpty()) {
                Toast.makeText(this, "Adınızı Giriniz", Toast.LENGTH_LONG).show()
            } else if (edName.text.contentEquals("Emre")  ||
                edName.text.contentEquals("EMRE") ||
                edName.text.contentEquals("Elif")  ||
                edName.text.contentEquals("ELİF")) {
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME, edName.text)
                startActivity(intent)
                finish()
            }else {
                Toast.makeText(this, "Sadece Elif veya Emre oynayabilir", Toast.LENGTH_LONG).show()
            }
        }
    }

}