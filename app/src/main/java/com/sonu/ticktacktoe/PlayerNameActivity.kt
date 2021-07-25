package com.sonu.ticktacktoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_player_name.*

class PlayerNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_name)
        play_btn.setOnClickListener {
            if (first_player_name.editText!!.text.isNullOrEmpty()) {
                Toast.makeText(this, "Please input the first player name", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else if (second_player_name.editText!!.text.isNullOrEmpty()) {
                Toast.makeText(this, "Please input the first player name", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else {
                Log.d("TAG", "onCreate: "+first_player_name.editText!!.text)
                Log.d("TAG", "onCreate: "+second_player_name.editText!!.text)
                startActivity(Intent(this, MainActivity::class.java).apply {
                    putExtra(Constants.FIRST_PLAYER_NAME, first_player_name.editText!!.text.toString())
                    putExtra(Constants.SECOND_PLAYER_NAME, second_player_name.editText!!.text.toString())
                }
                )
            }
        }
    }
}