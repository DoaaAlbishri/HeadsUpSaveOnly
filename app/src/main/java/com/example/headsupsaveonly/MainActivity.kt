package com.example.headsupsaveonly

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Surface
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    //initialize UI elements
    private lateinit var llTop: LinearLayout
    private lateinit var llMain: LinearLayout
    private lateinit var llCelebrity: LinearLayout
    private lateinit var tvTime: TextView
    private lateinit var tvName: TextView
    private lateinit var tvTaboo1: TextView
    private lateinit var tvTaboo2: TextView
    private lateinit var tvTaboo3: TextView
    private lateinit var tvMain: TextView
    private lateinit var btStart: Button
    private lateinit var btAdd: Button
    //start game
    private var gameStarted = false
    //array of celebrities
    private lateinit var celebrities: ArrayList<CelebrityDetails.Celebrity>
    var celebrity = arrayListOf<CelebrityDetails.Celebrity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //declare UI elements
        llTop = findViewById(R.id.llTop)
        llMain = findViewById(R.id.llMain)
        llCelebrity = findViewById(R.id.llCelebrity)
        tvTime = findViewById(R.id.tvTime)
        tvName = findViewById(R.id.tvName)
        tvTaboo1 = findViewById(R.id.tvTaboo1)
        tvTaboo2 = findViewById(R.id.tvTaboo2)
        tvTaboo3 = findViewById(R.id.tvTaboo3)
        tvMain = findViewById(R.id.tvMain)
        btStart = findViewById(R.id.btStart)
        btAdd = findViewById(R.id.btAdd)
        //array of celebrities
        celebrities = arrayListOf()
        //on click start button
        btStart.setOnClickListener {
            fetchCelebritiesData()
            newTimer()
        }
        btAdd.setOnClickListener {
            intent = Intent(applicationContext, AddCelebrity::class.java)
            startActivity(intent)
        }
    }
    private fun fetchCelebritiesData(){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        val call: Call<ArrayList<CelebrityDetails.Celebrity>> = apiInterface!!.getCelebrity()

        call?.enqueue(object : Callback<ArrayList<CelebrityDetails.Celebrity>> {
            override fun onResponse(
                    call: Call<ArrayList<CelebrityDetails.Celebrity>>,
                    response: Response<ArrayList<CelebrityDetails.Celebrity>>
            ) {
                celebrities = response.body()!!
            }

            override fun onFailure(call: Call<ArrayList<CelebrityDetails.Celebrity>>, t: Throwable) {
                Toast.makeText(applicationContext, "" + t.message, Toast.LENGTH_SHORT).show();
            }
        })
    }
    //timer
    private fun newTimer(){
        if(!gameStarted){
            gameStarted = true
            tvMain.text = "Please Rotate Device"
            btStart.isVisible = false
            val rotation = windowManager.defaultDisplay.rotation
            if(rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180){
                updateInterface(false)
            }else{
                updateInterface(true)
            }
            //https://developer.android.com/reference/android/os/CountDownTimer
            object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    tvTime.text = "Time: ${millisUntilFinished / 1000}"
                }

                override fun onFinish() {
                    gameStarted = false
                    tvTime.text = "Time: --"
                    tvMain.text = "Heads Up!"
                    btStart.isVisible = true
                    updateInterface(false)
                }
            }.start()
        }
    }
    //rotate device
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val rotation = windowManager.defaultDisplay.rotation
        if(rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180){
            if(gameStarted){
                updateInterface(false)
            }else{
                updateInterface(false)
            }
        }else{
            if(gameStarted){
                newCelebrity()
                updateInterface(true)
            }else{
                updateInterface(false)
            }
        }
    }

    private fun newCelebrity(){
        val celeb = celebrities[Random.nextInt(0,celebrities.size)]
        if(celeb !in celebrity)
            celebrity.add(celeb)
        tvName.text = celeb.name
        tvTaboo1.text = celeb.taboo1
        tvTaboo2.text = celeb.taboo2
        tvTaboo3.text = celeb.taboo3
    }

    private fun updateInterface(states: Boolean){
        // states: false --> start button is visible , true --> Celebrity is visible
        if(states){
            llCelebrity.isVisible = true
            llMain.isVisible = false
        }else{
            llCelebrity.isVisible = false
            llMain.isVisible = true
        }
    }
}