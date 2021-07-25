package com.sonu.ticktacktoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    lateinit var board: Array<Array<Button>>
    var PLAYER = true
    var TURN_COUNT = 0
    private val TAG = "MainActivity"
    var boardStatus = Array(3) { IntArray(3) }

    lateinit var FIRST_PLAYER_NAME: String
    lateinit var SECOND_PLAYER_NAME: String
    lateinit var players_Name: Array<String>
    var PLAYER_TURN_NAME = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FIRST_PLAYER_NAME = intent.getStringExtra(Constants.FIRST_PLAYER_NAME)!!
        SECOND_PLAYER_NAME = intent.getStringExtra(Constants.SECOND_PLAYER_NAME)!!
        PLAYER_TURN_NAME = FIRST_PLAYER_NAME
        players_Name = arrayOf(FIRST_PLAYER_NAME, SECOND_PLAYER_NAME)
        val playerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, players_Name)

        turn_player_name.text = "${PLAYER_TURN_NAME}'s Turn"
        playerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(first_player_selection_spinner)
        {
            adapter = playerAdapter
            setSelection(0, false)
            onItemSelectedListener = this@MainActivity

        }

        board = arrayOf(
            arrayOf(button1, button2, button3),
            arrayOf(button4, button5, button6),
            arrayOf(button7, button8, button9),
        )
        initializeBoardStatus();

        for (i in board) {
            for (button in i) {
                button.setOnClickListener(this)
            }
        }

        reset.setOnClickListener {
            PLAYER = true
            TURN_COUNT = 0
            turn_player_name.text = "${FIRST_PLAYER_NAME}'s Turn"
            first_player_selection_spinner.isEnabled = true
            first_player_selection_spinner.setSelection(0)
            initializeBoardStatus()
        }

    }

    private fun initializeBoardStatus() {
        for (i in 0..2) {
            for (j in 0..2) {
                boardStatus[i][j] = -1;
            }
        }

        for (i in board) {
            for (button in i) {
                button.isEnabled = true
                button.text = ""
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button1 -> {
                updateValue(row = 0, col = 0, player = PLAYER)
            }


            R.id.button2 -> {
                updateValue(row = 0, col = 1, player = PLAYER)

            }

            R.id.button3 -> {
                updateValue(row = 0, col = 2, player = PLAYER)

            }
            R.id.button4 -> {
                updateValue(row = 1, col = 0, player = PLAYER)

            }
            R.id.button5 -> {
                updateValue(row = 1, col = 1, player = PLAYER)

            }
            R.id.button6 -> {
                updateValue(row = 1, col = 2, player = PLAYER)

            }
            R.id.button7 -> {
                updateValue(row = 2, col = 0, player = PLAYER)

            }
            R.id.button8 -> {
                updateValue(row = 2, col = 1, player = PLAYER)

            }
            R.id.button9 -> {
                updateValue(row = 2, col = 2, player = PLAYER)

            }
        }
        TURN_COUNT++;
        PLAYER = !PLAYER
        if (PLAYER) {
            updateDisplay("${FIRST_PLAYER_NAME}'s Turn");
        } else {
            updateDisplay("${SECOND_PLAYER_NAME}'s Turn");

        }
        if (TURN_COUNT == 9) {
            updateDisplay("Game Draw");

        }
        checkWinner()
    }

    private fun checkWinner() {
        //horizontal rows

        for (i in 0..2) {
            if (boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2]) {
                if (boardStatus[i][0] == 1) {
                    updateDisplay("${FIRST_PLAYER_NAME} is Winner")
                    break
                } else if (boardStatus[i][0] == 0) {
                    updateDisplay("${SECOND_PLAYER_NAME} is Winner")
                    break
                }
            }
        }

        //vertical columns

        for (i in 0..2) {
            if (boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i]) {
                if (boardStatus[0][i] == 1) {
                    updateDisplay("${FIRST_PLAYER_NAME} is Winner")
                    break
                } else if (boardStatus[0][i] == 0) {
                    updateDisplay("${SECOND_PLAYER_NAME} is Winner")
                    break
                }
            }
        }
        //first diagnol

        for (i in 0..2) {
            if (boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2]) {
                if (boardStatus[0][0] == 1) {
                    updateDisplay("${FIRST_PLAYER_NAME}  is Winner")
                    break
                } else if (boardStatus[0][0] == 0) {
                    updateDisplay("${SECOND_PLAYER_NAME} is Winner")
                    break
                }
            }
        }

        //second diagnol

        for (i in 0..2) {
            if (boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0]) {
                if (boardStatus[0][2] == 1) {
                    updateDisplay("${FIRST_PLAYER_NAME} is Winner")
                    break
                } else if (boardStatus[0][2] == 0) {
                    updateDisplay("${SECOND_PLAYER_NAME} is Winner")
                    break
                }
            }
        }


    }

    private fun updateDisplay(s: String) {
        turn_player_name.text = s
        if (s.contains("Winner")) {
            disableButton();
        }
    }

    private fun disableButton() {
        for (i in board) {
            for (button in i) {
                button.isEnabled = false
            }
        }
    }

    private fun updateValue(row: Int, col: Int, player: Boolean) {
        first_player_selection_spinner.isEnabled = false
        val text = if (player) "X" else "O"
        val value = if (player) 1 else 0
        board[row][col].apply {
            isEnabled = false
            setText(text)
        }
        boardStatus[row][col] = value
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> {
                turn_player_name.text = "${FIRST_PLAYER_NAME} 's Turn"
                PLAYER_TURN_NAME = FIRST_PLAYER_NAME
            }
            1 -> {
                turn_player_name.text = "${SECOND_PLAYER_NAME} 's Turn"
                PLAYER_TURN_NAME = SECOND_PLAYER_NAME
            }
        }
        gameStartedOrNot()
    }

    private fun gameStartedOrNot() {

        var gameStarted = 0
        for (i in 0..2) {
            for (j in 0..2) {
                if (boardStatus[i][j] != -1) {
                    first_player_selection_spinner.isEnabled = false
                    return
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d(TAG, "onNothingSelected: ")
    }
}